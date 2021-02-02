package com.vaadin.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = Lumo.class)
@Route("")
@PWA(
        name = "Crypto Client",
        shortName = "",
        offlineResources = {
                "./styles/offline.css",
                "./images/offline.png"
        },
        enableInstallPrompt = false
)
@CssImport("./styles/styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Crypto Client");
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLinkWallets = new RouterLink("Wallets", Wallets.class);
        listLinkWallets.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink listLinkWalletItems = new RouterLink("Wallet Items", WalletItems.class);
        listLinkWallets.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink listExchangePortals = new RouterLink("Exchange Portals", ExchangePortals.class);
        listLinkWallets.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink listItemsToBuy = new RouterLink("Items to buy", ItemsToBuy.class);
        listLinkWallets.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink listLink = new RouterLink("All data", AllData.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listLinkWallets,
                listLinkWalletItems,
                listExchangePortals,
                listItemsToBuy,
                listLink,
                new RouterLink("Analyzer", Analyzer.class)
        ));
    }
}