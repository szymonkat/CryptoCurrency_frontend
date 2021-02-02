package com.vaadin.ui;

import com.vaadin.client.ItemToBuyClient;
import com.vaadin.domain.ItemFinalize;
import com.vaadin.dto.WalletDto;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import java.util.List;


@Route(value = "itemToBuyFinalize", layout = MainLayout.class)
public class ItemToBuyFinalize extends FormLayout {

    @PropertyId("idValue")
    ComboBox<Long> idValue = new ComboBox<>("Select Item to Buy You want to finalize");
    ComboBox<WalletDto> walletDto = new ComboBox<>("Choose wallet owner");


    Button save = new Button("Save");
    Button finalize = new Button("Finalize");
    Button close = new Button("Cancel");

    Binder<ItemFinalize> binder = new BeanValidationBinder<>(ItemFinalize.class);
    private ItemFinalize itemFinalize;


    public ItemToBuyFinalize(List<WalletDto> walletList,
                             List<Long> itemToBuyLongList,
                             ItemToBuyClient itemToBuyClient) {
        addClassName("item-to-buy-finalize");
        binder.bindInstanceFields(this);
        idValue.setItems(itemToBuyLongList);
        idValue.setItemLabelGenerator(Long -> itemToBuyClient.getItemToBuyById(Long).toString());
        walletDto.setItems(walletList);
        walletDto.setItemLabelGenerator(WalletDto::toString);
        Text text = new Text("You cannot finalize items which are linked with ExchangePortal older than 20 min!");
        add(
                idValue,
                walletDto,
                createButtonsLayout(),
                text
        );
    }

    public void setItemFinalize(ItemFinalize itemFinalize) {
        this.itemFinalize = itemFinalize;
        binder.readBean(itemFinalize);
    }

    private Component createButtonsLayout() {
        finalize.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        finalize.addClickListener(click -> finalizeAction());
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(finalize, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(itemFinalize);
            fireEvent(new SaveEvent(this, itemFinalize));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void finalizeAction() {
        validateAndSave();
        fireEvent(new FinalizeEvent(this, itemFinalize));
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    // Events
    public static abstract class ItemToBuyFinalizeEvent extends ComponentEvent<ItemToBuyFinalize> {
        private final ItemFinalize itemFinalize;

        protected ItemToBuyFinalizeEvent(ItemToBuyFinalize source, ItemFinalize itemFinalize) {
            super(source, false);
            this.itemFinalize = itemFinalize;
        }

        public ItemFinalize getItemFinalize() {
            return itemFinalize;
        }
    }

    public static class SaveEvent extends ItemToBuyFinalizeEvent {
        SaveEvent(ItemToBuyFinalize source, ItemFinalize itemFinalize) {
            super(source, itemFinalize);
        }
    }

    public static class FinalizeEvent extends ItemToBuyFinalizeEvent {
        FinalizeEvent(ItemToBuyFinalize source, ItemFinalize itemFinalize) {
            super(source, itemFinalize);
        }
    }

    public static class CloseEvent extends ItemToBuyFinalizeEvent {
        CloseEvent(ItemToBuyFinalize source) {
            super(source, null);
        }
    }
}
