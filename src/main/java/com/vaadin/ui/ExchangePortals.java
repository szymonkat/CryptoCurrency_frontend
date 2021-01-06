package com.vaadin.ui;

import com.vaadin.client.ApiService;
import com.vaadin.client.ServiceFactory;
import com.vaadin.domain.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.service.implementations.ExchangePortalServiceImpl;
import com.vaadin.service.interfaces.ExchangePortalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CssImport("./styles/styles.css")
@Route(value = "exchangePortals", layout = MainLayout.class)
@PageTitle("Exchange Portals")
public class ExchangePortals extends VerticalLayout {

    private ExchangePortalForm exchangePortalForm;
    private ExchangePortalService exchangePortalService;
    @Autowired
    private ServiceFactory serviceFactory;

    final Grid<ExchangePortal> exchangePortalGrid = new Grid<>(ExchangePortal.class);

    public ExchangePortals(ExchangePortalService exchangePortalService) {
        this.exchangePortalService = exchangePortalService;

        addClassName("list-view");
        setSizeFull();
        configureExchangePortalGrid();
        
        Label exchangePortalLabel = new Label("Get Your latest Crypto ratings:");
        exchangePortalLabel.setClassName("labels");
        add(exchangePortalLabel, exchangePortalGrid);
        updateList();

        // Create arguments for Form entries
        List<String> providerList = new ArrayList<>();
        providerList.add("nomics");
        providerList.add("coinlayer");
        List<Currency> enumValues = new ArrayList<>();
        enumValues.add(Currency.XMR);
        enumValues.add(Currency.BTC);
        Currency usd = Currency.USD;


        exchangePortalForm = new ExchangePortalForm(providerList, enumValues, usd);
        exchangePortalForm.addListener(ExchangePortalForm.SaveEvent.class, this::saveExchangePortal);
        exchangePortalForm.addListener(ExchangePortalForm.DeleteEvent.class, this::deleteExchangePortal);
        exchangePortalForm.addListener(ExchangePortalForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(exchangePortalGrid, exchangePortalForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void deleteExchangePortal(ExchangePortalForm.DeleteEvent evt) {
        exchangePortalService.delete(evt.getExchangePortal().getId());
        updateList();
        closeEditor();
    }

    private void saveExchangePortal(ExchangePortalForm.SaveEvent evt) {
        ApiService apiService = serviceFactory.createService(evt.getExchangePortal().getProvider());
        exchangePortalService.save(apiService.createExchangePortal(evt.getExchangePortal().getCurrencyToBuy()));
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addExchangePortalButton = new Button("Get Crypto ratings", click -> addExchangePortal());

        HorizontalLayout toolbar = new HorizontalLayout(addExchangePortalButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addExchangePortal() {
        exchangePortalGrid.asSingleSelect().clear();
        editExchangePortal(new ExchangePortal());
    }

    private void editExchangePortal(ExchangePortal exchangePortal) {
        if (exchangePortal == null) {
            closeEditor();
        } else {
            exchangePortalForm.setExchangePortal(exchangePortal);
            exchangePortalForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        exchangePortalForm.setExchangePortal(null);
        exchangePortalForm.setVisible(false);
        removeClassName("editing");
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
        exchangePortalGrid.asSingleSelect().addValueChangeListener(evt -> editExchangePortal(evt.getValue()));
    }

    private void updateList() {
        exchangePortalGrid.setItems(exchangePortalService.getExchangePortals());
    }
}