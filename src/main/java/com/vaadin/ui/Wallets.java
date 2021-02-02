package com.vaadin.ui;

import com.vaadin.client.WalletClient;
import com.vaadin.dto.WalletDto;
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


@CssImport("./styles/styles.css")
@Route(value = "wallets", layout = MainLayout.class)
@PageTitle("Wallets")
public class Wallets extends VerticalLayout {

    final Grid<WalletDto> walletGrid = new Grid<>(WalletDto.class);
    private final WalletForm form;
    @Autowired
    private final WalletClient walletClient;

    public Wallets(WalletClient walletClient) {
        this.walletClient = walletClient;

        addClassName("list-view");
        setSizeFull();
        configureWalletGrid();

        Label walletLabel1 = new Label("Create Your wallet by typing unique name.");
        Label walletLabel2 = new Label("If You want to delete existing wallet, select it manually and click 'delete'.");
        Label walletLabel3 = new Label("If You want to edit Your wallet's name, first click on the item," +
                " change name in the textfield and finally click 'edit'.");


        walletLabel1.setClassName("labels");
        walletLabel2.setClassName("labels");
        walletLabel3.setClassName("labels");
        add(walletLabel1, walletLabel2, walletLabel3, walletGrid);
        updateList();

        form = new WalletForm();
        form.addListener(WalletForm.SaveEvent.class, this::saveWallet);
        form.addListener(WalletForm.DeleteEvent.class, this::deleteWallet);
        form.addListener(WalletForm.EditEvent.class, this::editWallet);
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

    private void editWallet(WalletForm.EditEvent evt) {
        WalletDto walletDto = evt.getWalletDto();
        walletDto.setName(form.name.getValue());
        walletClient.updateWallet(walletDto);
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addWalletDtoButton = new Button("Wallet Menu", click -> addWalletDto());

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