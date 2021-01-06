package com.vaadin.ui;

import com.vaadin.domain.Currency;
import com.vaadin.domain.Wallet;
import com.vaadin.domain.WalletItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.service.interfaces.WalletItemService;
import com.vaadin.service.interfaces.WalletService;

import java.util.Arrays;
import java.util.List;

@CssImport("./styles/styles.css")
@Route(value = "walletItems", layout = MainLayout.class)
@PageTitle("Wallet Items")
public class WalletItems extends VerticalLayout {

    private WalletItemForm walletItemForm;

    final Grid<WalletItem> walletItemGrid = new Grid<>(WalletItem.class);

    public WalletItems() {

        addClassName("list-view");
        setSizeFull();
        configureWalletItemGrid();

        Label walletItemLabel = new Label("Top up wallet funds. If You want to delete existing wallet item, select it manually and click \'delete\' button.");
        walletItemLabel.setClassName("labels");
        add(walletItemLabel, walletItemGrid);
        updateList();

        // Create arguments for Form entries
        List<Wallet> walletList = walletService.getWallets();
        List<Currency> enumValues = Arrays.asList(Currency.values());

        walletItemForm = new WalletItemForm(walletList, enumValues);
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
        walletItemService.deleteWalletItem(evt.getWalletItem().getId());
        updateList();
        closeEditor();
    }

    private void saveWalletItem(WalletItemForm.SaveEvent evt) {
        walletItemService.modifyWalletItem(evt.getWalletItem());
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
        editWalletItem(new WalletItem());
    }

    private void editWalletItem(WalletItem walletItem) {
        if (walletItem == null) {
            closeEditor();
        } else {
            walletItemForm.setWalletItem(walletItem);
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
        walletItemGrid.setColumns("id", "currency", "quantity", "wallet");
        walletItemGrid.getDataProvider().refreshAll();
        walletItemGrid.getColumns().get(0).setFlexGrow(2);
        walletItemGrid.getColumns().get(1).setFlexGrow(3);
        walletItemGrid.getColumns().get(2).setFlexGrow(4);
        walletItemGrid.getColumns().get(3).setFlexGrow(10);
        walletItemGrid.asSingleSelect().addValueChangeListener(evt -> editWalletItem(evt.getValue()));
    }

    private void updateList() {
        walletItemGrid.setItems(walletItemService.getWalletItems());
    }
}