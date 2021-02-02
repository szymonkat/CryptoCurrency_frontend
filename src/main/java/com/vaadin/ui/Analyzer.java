package com.vaadin.ui;

import com.vaadin.client.AnalyzerClient;
import com.vaadin.client.ExchangePortalClient;
import com.vaadin.domain.Currency;
import com.vaadin.dto.ExchangePortalDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Analyzer")
@Route(value = "analyzer", layout = MainLayout.class)
public class Analyzer extends VerticalLayout {

    @Autowired
    private final AnalyzerClient analyzerClient;

    @Autowired
    private final ExchangePortalClient exchangePortalClient;

    public Analyzer(AnalyzerClient analyzerClient, ExchangePortalClient exchangePortalClient) {
        this.analyzerClient = analyzerClient;
        this.exchangePortalClient = exchangePortalClient;

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
            ExchangePortalDto exchangePortalDto = analyzerClient.getMinValueExchangePortal(Currency.XMR);
            data = exchangePortalDto.toString();
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
            ExchangePortalDto exchangePortalDto = analyzerClient.getMaxValueExchangePortal(Currency.XMR);
            data = exchangePortalDto.toString();
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
            ExchangePortalDto exchangePortalDto = analyzerClient.getMaxValueExchangePortal(Currency.BTC);
            data = exchangePortalDto.toString();
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
            ExchangePortalDto exchangePortalDto = analyzerClient.getMinValueExchangePortal(Currency.BTC);
            data = exchangePortalDto.toString();
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
            ExchangePortalDto exchangePortalDto = analyzerClient.getOldestValueExchangePortal(Currency.XMR);
            data = exchangePortalDto.toString();
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
            ExchangePortalDto exchangePortalDto = analyzerClient.getNewestValueExchangePortal(Currency.XMR);
            data = exchangePortalDto.toString();
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
            ExchangePortalDto exchangePortalDto = analyzerClient.getOldestValueExchangePortal(Currency.BTC);
            data = exchangePortalDto.toString();
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
            ExchangePortalDto exchangePortalDto = analyzerClient.getNewestValueExchangePortal(Currency.BTC);
            data = exchangePortalDto.toString();
        }
        Details component = new Details("BTC newest ratio",
                new Text("Newest BTC:  " + data));
        component.setOpened(true);
        return component;
    }

    private boolean checkIfCurrencyExchangePortalExists(Currency currency) {
        return exchangePortalClient.getExchangePortalsWithCurrency(currency).isEmpty();
    }
}
