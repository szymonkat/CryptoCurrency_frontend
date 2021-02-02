package com.vaadin.ui;

import com.vaadin.client.ExchangePortalClient;
import com.vaadin.dto.ItemToBuyDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import java.util.List;

@Route(value = "itemToBuySave", layout = MainLayout.class)
public class ItemToBuySave extends FormLayout {

    ComboBox<Long> exchangePortalId = new ComboBox<>("ExchangePortal");
    NumberField quantityToBuy = new NumberField("Quantity to buy");


    Button save = new Button("Save");
    Button close = new Button("Cancel");

    Binder<ItemToBuyDto> binder = new BeanValidationBinder<>(ItemToBuyDto.class);
    private ItemToBuyDto itemToBuyDto;


    public ItemToBuySave(List<Long> exchangePortalList, ExchangePortalClient exchangePortalClient) {
        addClassName("item-to-buy-save");
        binder.bindInstanceFields(this);
        exchangePortalId.setItems(exchangePortalList);
        exchangePortalId.setItemLabelGenerator(Long -> exchangePortalClient.getExchangePortalById(Long).toString());
        quantityToBuy.setValue(1d);
        quantityToBuy.setHasControls(true);
        quantityToBuy.setMin(1);


        add(
                exchangePortalId,
                quantityToBuy,
                createButtonsLayout()
        );
    }

    public void setItemToBuy(ItemToBuyDto itemToBuyDto) {
        this.itemToBuyDto = itemToBuyDto;
        binder.readBean(itemToBuyDto);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(itemToBuyDto);
            fireEvent(new SaveEvent(this, itemToBuyDto));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    // Events
    public static abstract class ItemToBuySaveEvent extends ComponentEvent<ItemToBuySave> {
        private final ItemToBuyDto itemToBuyDto;

        protected ItemToBuySaveEvent(ItemToBuySave source, ItemToBuyDto itemToBuyDto) {
            super(source, false);
            this.itemToBuyDto = itemToBuyDto;
        }

        public ItemToBuyDto getItemToBuy() {
            return itemToBuyDto;
        }
    }

    public static class SaveEvent extends ItemToBuySaveEvent {
        SaveEvent(ItemToBuySave source, ItemToBuyDto itemToBuyDto) {
            super(source, itemToBuyDto);
        }
    }

    public static class CloseEvent extends ItemToBuySaveEvent {
        CloseEvent(ItemToBuySave source) {
            super(source, null);
        }
    }
}
