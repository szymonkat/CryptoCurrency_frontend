package com.vaadin.ui;

import com.vaadin.domain.Currency;
import com.vaadin.domain.ExchangePortal;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.service.interfaces.AnalyzerService;
import com.vaadin.service.interfaces.ExchangePortalService;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Analyzer")
@Route(value = "analyzer", layout = MainLayout.class)
public class Analyzer extends VerticalLayout {

    @Autowired
    private AnalyzerService analyzerService;
    @Autowired
    private ExchangePortalService exchangePortalService;

    public Analyzer(ExchangePortalService exchangePortalService,
                    AnalyzerService analyzerService) {

        this.analyzerService = analyzerService;
        this.exchangePortalService = exchangePortalService;

        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(
                getMinValueXmr(),
                getMaxValueXmr(),
                getMinValueBtc(),
                getMaxValueBtc(),
                getNewestXmr(),
                getOldestXmr(),
                getNewestBtc(),
                getOldestBtc()
        );
    }

    private Details getMinValueXmr() {
        String data = "";
        if (checkIfCurrencyExchangePortalExists(Currency.XMR)) {
            data = "There is no XMR exchange Portals";
        } else {
            ExchangePortal exchangePortal = analyzerService.findMinRatio(Currency.XMR);
            data = exchangePortal.toString();
        }
        Details component = new Details("XMR cheapest ratio",
                new Text("Cheapest XMR: " + data));
        component.setOpened(true);
        return component;
    }

    private Details getMaxValueXmr() {
        String data = "";
        if (checkIfCurrencyExchangePortalExists(Currency.XMR)) {
            data = "There is no XMR exchange Portals";
        } else {
            ExchangePortal exchangePortal = analyzerService.findMaxRatio(Currency.XMR);
            data = exchangePortal.toString();
        }
        Details component = new Details("XMR most expensive ratio",
                new Text("Most expensive XMR: " + data));
        component.setOpened(true);
        return component;
    }

    private Details getMaxValueBtc() {
        String data = "";
        if (checkIfCurrencyExchangePortalExists(Currency.BTC)) {
            data = "There is no BTC exchange Portals";
        } else {
            ExchangePortal exchangePortal = analyzerService.findMaxRatio(Currency.BTC);
            data = exchangePortal.toString();
        }
        Details component = new Details("BTC most expensive ratio",
                new Text("Most expensive BTC: " + data));
        component.setOpened(true);
            return component;
    }

    private Details getMinValueBtc() {
        String data = "";
        if (checkIfCurrencyExchangePortalExists(Currency.BTC)) {
            data = "There is no BTC exchange Portals";
        } else {
            ExchangePortal exchangePortal = analyzerService.findMinRatio(Currency.BTC);
            data = exchangePortal.toString();
        }
        Details component = new Details("BTC cheapest ratio",
                new Text("Cheapest BTC: " + data));
        component.setOpened(true);
        return component;
    }

    private Details getOldestXmr() {
        String data = "";
        if (checkIfCurrencyExchangePortalExists(Currency.XMR)) {
            data = "There is no XMR exchange Portals";
        } else {
            ExchangePortal exchangePortal = analyzerService.findOldestRatio(Currency.XMR);
            data = exchangePortal.toString();
        }
        Details component = new Details("XMR oldest ratio",
                new Text("Oldest XMR:  " + data));
        component.setOpened(true);
        return component;
    }

    private Details getNewestXmr() {
        String data = "";
        if (checkIfCurrencyExchangePortalExists(Currency.XMR)) {
            data = "There is no XMR exchange Portals";
        } else {
            ExchangePortal exchangePortal = analyzerService.findNewestRatio(Currency.XMR);
            data = exchangePortal.toString();
        }
        Details component = new Details("XMR newest ratio",
                new Text("Newest XMR:  " + data));
        component.setOpened(true);
        return component;
    }

    private Details getOldestBtc() {
        String data = "";
        if (checkIfCurrencyExchangePortalExists(Currency.BTC)) {
            data = "There is no BTC exchange Portals";
        } else {
            ExchangePortal exchangePortal = analyzerService.findOldestRatio(Currency.BTC);
            data = exchangePortal.toString();
        }
        Details component = new Details("BTC oldest ratio",
                new Text("Oldest BTC:  " + data));
        component.setOpened(true);
        return component;
    }

    private Details getNewestBtc() {
        String data = "";
        if (checkIfCurrencyExchangePortalExists(Currency.BTC)) {
            data = "There is no BTC exchange Portals";
        } else {
            ExchangePortal exchangePortal = analyzerService.findNewestRatio(Currency.BTC);
            data = exchangePortal.toString();
        }
        Details component = new Details("BTC newest ratio",
                new Text("Newest BTC:  " + data));
        component.setOpened(true);
        return component;
    }

    private boolean checkIfCurrencyExchangePortalExists(Currency currency) {
        return exchangePortalService.getExchangePortalsWithCurrency(currency).isEmpty();
    }
}