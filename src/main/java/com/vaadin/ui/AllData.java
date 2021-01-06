package com.vaadin.ui;

import com.vaadin.domain.ExchangePortal;
import com.vaadin.domain.ItemToBuy;
import com.vaadin.domain.Wallet;
import com.vaadin.domain.WalletItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@CssImport("./styles/styles.css")
@Route(value = "allData", layout = MainLayout.class)
@PageTitle("All data")
public class AllData extends VerticalLayout {


    final Grid<Wallet> walletGrid = new Grid<>(Wallet.class);
    final Grid<WalletItem> walletItemGrid = new Grid<>(WalletItem.class);
    final Grid<ExchangePortal> exchangePortalGrid = new Grid<>(ExchangePortal.class);
    final Grid<ItemToBuy> itemToBuyGrid = new Grid<>(ItemToBuy.class);

    public AllData() {

        addClassName("list-view");
        setSizeFull();
        configureAllLists();
        Label walletLabel = new Label("List of wallets:");
        Label walletItemLabel = new Label("List of wallet items:");
        Label exchangePortalLabel = new Label("List of exchange portals:");
        Label itemToBuyLabel = new Label("List of item to buy's:");
        walletLabel.setClassName("labels");
        add(walletLabel, walletGrid, walletItemLabel, walletItemGrid, exchangePortalLabel, exchangePortalGrid, itemToBuyLabel, itemToBuyGrid);
        updateLists();
    }

    private void configureWalletGrid() {
        walletGrid.addClassName("wallet-grid");
        walletGrid.setSizeFull();
        walletGrid.setColumns("id", "name");
        walletGrid.getDataProvider().refreshAll();
        walletGrid.getColumns().get(0).setFlexGrow(2);
        walletGrid.getColumns().get(1).setFlexGrow(12);
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
    }

    private void configureExchangePortalGrid() {
        exchangePortalGrid.addClassName("exchange-portal-grid");
        exchangePortalGrid.setSizeFull();
        exchangePortalGrid.setColumns("id", "provider", "currencyToBuy", "currencyToPay", "ratio", "time", "itemToBuy");
        exchangePortalGrid.getDataProvider().refreshAll();
        exchangePortalGrid.getColumns().get(0).setFlexGrow(2);
        exchangePortalGrid.getColumns().get(1).setFlexGrow(4);
        exchangePortalGrid.getColumns().get(2).setFlexGrow(3);
        exchangePortalGrid.getColumns().get(3).setFlexGrow(3);
        exchangePortalGrid.getColumns().get(4).setFlexGrow(6);
        exchangePortalGrid.getColumns().get(5).setFlexGrow(10);
        exchangePortalGrid.getColumns().get(6).setFlexGrow(10);
    }

    private void configureItemToBuyGrid() {
        itemToBuyGrid.addClassName("item-to-buy-grid");
        itemToBuyGrid.setSizeFull();
        itemToBuyGrid.setColumns("id", "exchangePortal", "quantityToBuy");
        itemToBuyGrid.getDataProvider().refreshAll();
        itemToBuyGrid.getColumns().get(0).setFlexGrow(2);
        itemToBuyGrid.getColumns().get(1).setFlexGrow(12);
        itemToBuyGrid.getColumns().get(2).setFlexGrow(2);
    }

    private void configureAllLists() {
        configureWalletGrid();
        configureWalletItemGrid();
        configureExchangePortalGrid();
        configureItemToBuyGrid();
    }

    private void updateLists() {
        walletGrid.setItems();
        walletItemGrid.setItems();
        exchangePortalGrid.setItems();
        itemToBuyGrid.setItems();
    }
}