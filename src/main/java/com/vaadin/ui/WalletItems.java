package com.vaadin.ui;

import com.vaadin.client.WalletClient;
import com.vaadin.client.WalletItemClient;
import com.vaadin.domain.Currency;
import com.vaadin.dto.WalletDto;
import com.vaadin.dto.WalletItemDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CssImport("./styles/styles.css")
@Route(value = "walletItems", layout = MainLayout.class)
@PageTitle("Wallet Items")
public class WalletItems extends VerticalLayout {

    final Grid<WalletItemDto> walletItemGrid = new Grid<>(WalletItemDto.class);
    private final WalletItemForm walletItemForm;
    @Autowired
    private final WalletClient walletClient;
    @Autowired
    private final WalletItemClient walletItemClient;

    public WalletItems(WalletClient walletClient, WalletItemClient walletItemClient) {
        this.walletClient = walletClient;
        this.walletItemClient = walletItemClient;

        addClassName("list-view");
        setSizeFull();
        configureWalletItemGrid();

        Label walletItemLabel = new Label("Top up wallet funds. If You want to delete existing wallet item, select it manually and click 'delete' button.");
        walletItemLabel.setClassName("labels");
        add(walletItemLabel, walletItemGrid);
        updateList();

        // Create arguments for Form entries
        List<Long> walletIdList = walletClient.getWallets().stream()
                .map(n -> n.getId())
                .collect(Collectors.toList());

        List<Currency> enumValues = Arrays.asList(Currency.values());

        walletItemForm = new WalletItemForm(walletIdList, enumValues, walletClient);
        walletItemForm.addListener(WalletItemForm.SaveEvent.class, this::saveWalletItem);
        walletItemForm.addListener(WalletItemForm.DeleteEvent.class, this::deleteWalletItem);
        walletItemForm.addListener(WalletItemForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(walletItemGrid, walletItemForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void deleteWalletItem(WalletItemForm.DeleteEvent evt) {
        walletItemClient.deleteWalletItem(evt.getWalletItem().getId());
        updateList();
        closeEditor();
    }

    private void saveWalletItem(WalletItemForm.SaveEvent evt) {
        walletItemClient.createWalletItem(evt.getWalletItem());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addWalletItemButton = new Button("Add or delete wallet Item", click -> addWalletItem());

        HorizontalLayout toolbar = new HorizontalLayout(addWalletItemButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addWalletItem() {
        walletItemGrid.asSingleSelect().clear();
        editWalletItem(new WalletItemDto());
    }

    private void editWalletItem(WalletItemDto walletItemDto) {
        if (walletItemDto == null) {
            closeEditor();
        } else {
            walletItemForm.setWalletItem(walletItemDto);
            walletItemForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        walletItemForm.setWalletItem(null);
        walletItemForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureWalletItemGrid() {
        walletItemGrid.addClassName("wallet-item-grid");
        walletItemGrid.setSizeFull();
        walletItemGrid.setColumns("id", "currency", "quantity");
        walletItemGrid.addColumn(walletItemDto -> {
            WalletDto walletDto = walletClient.getWalletById(walletItemDto.getWalletId());
            return walletDto == null ? "-" : walletDto.toString();
        }).setHeader("Wallet");
        walletItemGrid.getDataProvider().refreshAll();
        walletItemGrid.getColumns().get(0).setFlexGrow(2);
        walletItemGrid.getColumns().get(1).setFlexGrow(3);
        walletItemGrid.getColumns().get(2).setFlexGrow(4);
        walletItemGrid.getColumns().get(3).setFlexGrow(10);
        walletItemGrid.asSingleSelect().addValueChangeListener(evt -> editWalletItem(evt.getValue()));
    }

    private void updateList() {
        walletItemGrid.setItems(walletItemClient.getWalletItems());
    }
}
