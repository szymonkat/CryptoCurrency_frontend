package com.vaadin.ui;

import com.vaadin.client.ExchangePortalClient;
import com.vaadin.client.ItemToBuyClient;
import com.vaadin.client.WalletClient;
import com.vaadin.domain.ItemFinalize;
import com.vaadin.domain.LongVal;
import com.vaadin.dto.ExchangePortalDto;
import com.vaadin.dto.ItemToBuyDto;
import com.vaadin.dto.WalletDto;
import com.vaadin.flow.component.UI;
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

import java.util.List;
import java.util.stream.Collectors;

@CssImport("./styles/styles.css")
@Route(value = "itemsToBuy", layout = MainLayout.class)
@PageTitle("Items to buy")
public class ItemsToBuy extends VerticalLayout {

    final Grid<ItemToBuyDto> itemToBuyGrid = new Grid<>(ItemToBuyDto.class);
    private final ItemToBuySave itemToBuySave;
    private final ItemToBuyDelete itemToBuyDelete;
    private final ItemToBuyFinalize itemToBuyFinalize;
    @Autowired
    private final ItemToBuyClient itemToBuyClient;
    @Autowired
    private final ExchangePortalClient exchangePortalClient;
    @Autowired
    private final WalletClient walletClient;

    public ItemsToBuy(ItemToBuyClient itemToBuyClient, ExchangePortalClient exchangePortalClient, WalletClient walletClient) {
        this.itemToBuyClient = itemToBuyClient;
        this.exchangePortalClient = exchangePortalClient;
        this.walletClient = walletClient;

        addClassName("list-view");
        setSizeFull();
        configureItemToBuyGrid();

        Label itemToBuyLabel = new Label("Create currency offers and finalize transactions: ");
        itemToBuyLabel.setClassName("labels");
        add(itemToBuyLabel, itemToBuyGrid);
        updateList();

        // Create arguments for Form entries
        List<Long> exchangePortalDtoList = exchangePortalClient.getExchangePortals().stream()
                .filter(m -> m.getItemToBuyDtoId() == null)
                .map(n -> n.getId())
                .collect(Collectors.toList());

        List<Long> itemToBuyDtoLongList = itemToBuyClient.getItemToBuys().stream()
                .map(n -> n.getId())
                .collect(Collectors.toList());

        List<WalletDto> walletDtoList = walletClient.getWallets();

        itemToBuySave = new ItemToBuySave(exchangePortalDtoList, exchangePortalClient);
        itemToBuySave.addListener(ItemToBuySave.SaveEvent.class, this::saveItemToBuy);
        itemToBuySave.addListener(ItemToBuySave.CloseEvent.class, e -> closeSaveEditor());

        itemToBuyDelete = new ItemToBuyDelete(itemToBuyDtoLongList, itemToBuyClient);
        itemToBuyDelete.addListener(ItemToBuyDelete.DeleteEvent.class, this::deleteItemToBuy);
        itemToBuyDelete.addListener(ItemToBuyDelete.CloseEvent.class, e -> closeDeleteEditor());

        itemToBuyFinalize = new ItemToBuyFinalize(walletDtoList, itemToBuyDtoLongList, itemToBuyClient);
        itemToBuyFinalize.addListener(ItemToBuyFinalize.FinalizeEvent.class, this::finalizeItemToBuy);
        itemToBuyFinalize.addListener(ItemToBuyFinalize.CloseEvent.class, e -> closeFinalizeEditor());

        Div content = new Div(itemToBuyGrid, itemToBuySave, itemToBuyDelete, itemToBuyFinalize);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeSaveEditor();
        closeDeleteEditor();
        closeFinalizeEditor();
    }

    private void saveItemToBuy(ItemToBuySave.SaveEvent evt) {
        itemToBuyClient.createItemToBuy(evt.getItemToBuy());
        updateList();
        closeSaveEditor();
        UI.getCurrent().getPage().reload();
    }

    private void deleteItemToBuy(ItemToBuyDelete.DeleteEvent evt) {
        long idValue = (evt.getLongVal().getIdValue());

        try {
            itemToBuyClient.deleteItemToBuy(idValue);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        updateList();
        closeDeleteEditor();
        UI.getCurrent().getPage().reload();
    }

    private void finalizeItemToBuy(ItemToBuyFinalize.FinalizeEvent evt) {
        Long itemToBuyId = evt.getItemFinalize().getIdValue();
        Long walletId = evt.getItemFinalize().getWalletDto().getId();

        try {
            itemToBuyClient.finalizeItemToBuy(itemToBuyId, walletId);
        } catch (Exception e) {
            System.out.println("Please check if item to buy Id :" + itemToBuyId + " and wallet id: " + walletId + " exists");
            System.out.println(e.toString());
        }
        updateList();
        closeFinalizeEditor();
        UI.getCurrent().getPage().reload();
    }

    private HorizontalLayout getToolBar() {
        Button addItemToBuyButton = new Button("Add item to buy", click -> addItemToBuyWindow());
        Button deleteItemToBuyButton = new Button("Delete item by Id", click -> deleteItemToBuyWindow());
        Button addFinalizeButton = new Button("Finalize transaction by Id", click -> finalizeItemToBuyWindow());

        HorizontalLayout toolbar = new HorizontalLayout(addItemToBuyButton, deleteItemToBuyButton, addFinalizeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addItemToBuyWindow() {
        itemToBuyGrid.asSingleSelect().clear();
        closeDeleteEditor();
        closeFinalizeEditor();
        editSaveItemToBuy(new ItemToBuyDto());
    }

    private void deleteItemToBuyWindow() {

        itemToBuyGrid.asSingleSelect().clear();
        closeSaveEditor();
        closeFinalizeEditor();
        editDeleteVal(new LongVal());
    }

    private void finalizeItemToBuyWindow() {

        itemToBuyGrid.asSingleSelect().clear();
        closeSaveEditor();
        closeDeleteEditor();
        editFinalizeVal(new ItemFinalize());
    }

    private void editSaveItemToBuy(ItemToBuyDto itemToBuyDto) {
        if (itemToBuyDto == null) {
            closeSaveEditor();
        } else {
            itemToBuySave.setItemToBuy(itemToBuyDto);
            itemToBuySave.setVisible(true);
            addClassName("editing");
        }
    }

    private void editDeleteVal(LongVal longVal) {
        if (longVal == null) {
            closeDeleteEditor();
        } else {
            itemToBuyDelete.setLongVal(longVal);
            itemToBuyDelete.setVisible(true);
            addClassName("editing2");
        }
    }

    private void editFinalizeVal(ItemFinalize itemFinalize) {
        if (itemFinalize == null) {
            closeFinalizeEditor();
        } else {
            itemToBuyFinalize.setItemFinalize(itemFinalize);
            itemToBuyFinalize.setVisible(true);
            addClassName("editing3");
        }
    }

    private void closeSaveEditor() {
        itemToBuySave.setItemToBuy(null);
        itemToBuySave.setVisible(false);
        removeClassName("editing");
    }

    private void closeDeleteEditor() {
        itemToBuyDelete.setLongVal(null);
        itemToBuyDelete.setVisible(false);
        removeClassName("editing2");
    }

    private void closeFinalizeEditor() {
        itemToBuyFinalize.setItemFinalize(null);
        itemToBuyFinalize.setVisible(false);
        removeClassName("editing3");
    }

    private void configureItemToBuyGrid() {
        itemToBuyGrid.addClassName("item-to-buy-grid");
        itemToBuyGrid.setSizeFull();
        itemToBuyGrid.setColumns("id", "quantityToBuy");
        itemToBuyGrid.addColumn(itemToBuyDto -> {
            ExchangePortalDto exchangePortalDto =
                    exchangePortalClient.getExchangePortalById(itemToBuyDto.getExchangePortalId());
            return exchangePortalDto == null ? "---" : exchangePortalDto.toString();
        }).setHeader("Exchange Portal");
        itemToBuyGrid.getColumns().get(0).setFlexGrow(2);
        itemToBuyGrid.getColumns().get(1).setFlexGrow(2);
        itemToBuyGrid.getColumns().get(2).setFlexGrow(12);
        itemToBuyGrid.getDataProvider().refreshAll();
    }

    private void updateList() {
        itemToBuyGrid.setItems(itemToBuyClient.getItemToBuys());
    }
}
