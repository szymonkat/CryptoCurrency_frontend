package com.vaadin.ui;

import com.vaadin.client.ExchangePortalClient;
import com.vaadin.client.ItemToBuyClient;
import com.vaadin.domain.Currency;
import com.vaadin.dto.ExchangePortalDto;
import com.vaadin.dto.ItemToBuyDto;
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

import java.util.ArrayList;
import java.util.List;

@CssImport("./styles/styles.css")
@Route(value = "exchangePortals", layout = MainLayout.class)
@PageTitle("Exchange Portals")
public class ExchangePortals extends VerticalLayout {

    final Grid<ExchangePortalDto> exchangePortalGrid = new Grid<>(ExchangePortalDto.class);
    private final ExchangePortalForm exchangePortalForm;
    @Autowired
    private final ExchangePortalClient exchangePortalClient;
    @Autowired
    private final ItemToBuyClient itemToBuyClient;

    public ExchangePortals(ExchangePortalClient exchangePortalClient, ItemToBuyClient itemToBuyClient) {
        this.exchangePortalClient = exchangePortalClient;
        this.itemToBuyClient = itemToBuyClient;

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
        exchangePortalClient.deleteExchangePortal(evt.getExchangePortal().getId());
        updateList();
        closeEditor();
    }

    private void saveExchangePortal(ExchangePortalForm.SaveEvent evt) {
        exchangePortalClient.createExchangePortal(evt.getExchangePortal().getCurrencyToBuy(),
                evt.getExchangePortal().getProvider());
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
        editExchangePortal(new ExchangePortalDto());
    }

    private void editExchangePortal(ExchangePortalDto exchangePortalDto) {
        if (exchangePortalDto == null) {
            closeEditor();
        } else {
            exchangePortalForm.setExchangePortal(exchangePortalDto);
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
        exchangePortalGrid.setColumns("id", "provider", "currencyToBuy", "currencyToPay", "ratio", "time");
        exchangePortalGrid.addColumn(exchangePortalDto -> {
            ItemToBuyDto itemToBuyDto = itemToBuyClient.getItemToBuyById(exchangePortalDto.getItemToBuyDtoId());
            return itemToBuyDto == null ? "---" : itemToBuyDto.getId();
        }).setHeader("Item To Buy Id");
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
        exchangePortalGrid.setItems(exchangePortalClient.getExchangePortals());
    }
}
