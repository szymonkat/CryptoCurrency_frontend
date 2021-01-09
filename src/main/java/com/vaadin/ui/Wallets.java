package com.vaadin.ui;

import com.vaadin.client.WalletClient;
import com.vaadin.dto.WalletDto;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;


@CssImport("./styles/styles.css")
@Route(value = "wallets", layout = MainLayout.class)
@PageTitle("Wallets")
public class Wallets extends VerticalLayout {

    private WalletForm form;

    @Autowired
    private WalletClient walletClient;

    final Grid<WalletDto> walletGrid = new Grid<>(WalletDto.class);

    public Wallets(WalletClient walletClient) {
        this.walletClient = walletClient;

        addClassName("list-view");
        setSizeFull();
        configureWalletGrid();

        Label walletLabel = new Label("Create Your wallet by typing unique name. If You want to delete existing wallet, select it manually and click \'delete\'.");

        walletLabel.setClassName("labels");
        add(walletLabel, walletGrid);
        updateList();

        form = new WalletForm();
        form.addListener(WalletForm.SaveEvent.class, this::saveWallet);
        form.addListener(WalletForm.DeleteEvent.class, this::deleteWallet);
        form.addListener(WalletForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(walletGrid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void deleteWallet(WalletForm.DeleteEvent evt) {
        walletClient.deleteWallet(evt.getWalletDto().getId());
        updateList();
        closeEditor();
    }

    private void saveWallet(WalletForm.SaveEvent evt) {
        walletClient.createWallet(evt.getWalletDto());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addWalletDtoButton = new Button("Add or delete wallet", click -> addWalletDto());

        HorizontalLayout toolbar = new HorizontalLayout(addWalletDtoButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addWalletDto() {
        walletGrid.asSingleSelect().clear();
        editWallet(new WalletDto());
    }

    private void editWallet(WalletDto walletDto) {
        if (walletDto == null) {
            closeEditor();
        } else {
            form.setWallet(walletDto);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setWallet(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void configureWalletGrid() {
        walletGrid.addClassName("wallet-grid");
        walletGrid.setSizeFull();
        walletGrid.setColumns("id", "name");
        walletGrid.getDataProvider().refreshAll();
        walletGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        walletGrid.getColumns().get(0).setFlexGrow(2);
        walletGrid.getColumns().get(1).setFlexGrow(12);
        walletGrid.asSingleSelect().addValueChangeListener(evt -> editWallet(evt.getValue()));
    }

    private void updateList() {
        walletGrid.setItems(walletClient.getWallets());
    }
}