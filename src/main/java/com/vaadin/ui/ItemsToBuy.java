package com.vaadin.ui;

import com.vaadin.controller.ItemToBuyController;
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
import com.vaadin.service.interfaces.ExchangePortalService;
import com.vaadin.service.interfaces.ItemToBuyService;
import com.vaadin.service.interfaces.WalletService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@CssImport("./styles/styles.css")
@Route(value = "itemsToBuy", layout = MainLayout.class)
@PageTitle("Items to buy")
public class ItemsToBuy extends VerticalLayout {
    
    private ItemToBuySave itemToBuySave;
    private ItemToBuyDelete itemToBuyDelete;
    private ItemToBuyFinalize itemToBuyFinalize;


    final Grid<ItemToBuy> itemToBuyGrid = new Grid<>(ItemToBuy.class);

    public ItemsToBuy()  {

        addClassName("list-view");
        setSizeFull();
        configureItemToBuyGrid();
        
        Label itemToBuyLabel = new Label("Create currency offers and finalize transactions: ");
        itemToBuyLabel.setClassName("labels");
        add(itemToBuyLabel, itemToBuyGrid);
        updateList();

        // Create arguments for Form entries
        List<ExchangePortal> exchangePortalList = exchangePortalService.getExchangePortals();
        List<Wallet> walletList = walletService.getWallets();
        
        itemToBuySave = new ItemToBuySave(exchangePortalList);
        itemToBuySave.addListener(ItemToBuySave.SaveEvent.class, this::saveItemToBuy);
        itemToBuySave.addListener(ItemToBuySave.CloseEvent.class, e -> closeSaveEditor());

        itemToBuyDelete = new ItemToBuyDelete();
        itemToBuyDelete.addListener(ItemToBuyDelete.DeleteEvent.class, this::deleteItemToBuy);
        itemToBuyDelete.addListener(ItemToBuyDelete.CloseEvent.class, e -> closeDeleteEditor());

        itemToBuyFinalize = new ItemToBuyFinalize(walletList);
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
        itemToBuyService.save(evt.getItemToBuy());
        updateList();
        closeSaveEditor();
    }

    private void deleteItemToBuy(ItemToBuyDelete.DeleteEvent evt) {
        long idValue = ((evt.getLongVal().getIdValue()).longValue());
        if (itemToBuyService.checkIfExists(idValue)) {
            itemToBuyService.deleteItemToBuy(idValue);
            updateList();
            closeDeleteEditor();
        } else {
            System.out.println("Item to buy with id:" + idValue + " not found");
        }
    }

    private void finalizeItemToBuy(ItemToBuyFinalize.FinalizeEvent evt) {
        Long itemToBuyId = ((evt.getItemFinalize().getIdValue()).longValue());
        Long walletId = ((evt.getItemFinalize().getWallet().getId()).longValue());

        if (itemToBuyService.checkIfExists(itemToBuyId) && (walletService.checkIfExistsById(walletId))) {
            itemToBuyService.finalizeItemToBuy(itemToBuyId, walletId);
            updateList();
            closeFinalizeEditor();
        } else {
            System.out.println("Please check if item to buy Id :" + itemToBuyId + " and wallet id: " + walletId + " exists");
        }
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
        editSaveItemToBuy(new ItemToBuy());
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

    private void editSaveItemToBuy(ItemToBuy itemToBuy) {
        if (itemToBuy == null) {
            closeSaveEditor();
        } else {
            itemToBuySave.setItemToBuy(itemToBuy);
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
        itemToBuyGrid.setColumns("id", "exchangePortal", "quantityToBuy");
        itemToBuyGrid.getColumns().get(0).setFlexGrow(2);
        itemToBuyGrid.getColumns().get(1).setFlexGrow(12);
        itemToBuyGrid.getColumns().get(2).setFlexGrow(2);
        itemToBuyGrid.getDataProvider().refreshAll();
    }

    private void updateList() {
        itemToBuyGrid.setItems(itemToBuyService.getItemToBuys());
    }
}