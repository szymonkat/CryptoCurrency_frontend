package com.vaadin.ui;

import com.vaadin.client.WalletClient;
import com.vaadin.domain.Currency;
import com.vaadin.dto.WalletItemDto;
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

@Route(value = "walletItemForm", layout = MainLayout.class)
public class WalletItemForm extends FormLayout {


    ComboBox<Currency> currency = new ComboBox<>("Currency");
    NumberField quantity = new NumberField("Quantity");
    ComboBox<Long> walletId = new ComboBox<>("Wallet id");

    Button save = new Button("Create");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<WalletItemDto> binder = new BeanValidationBinder<>(WalletItemDto.class);
    private WalletItemDto walletItemDto;

    public WalletItemForm(List<Long> walletIdList, List<Currency> currenciesList, WalletClient walletClient) {

        addClassName("wallet-item-form");
        binder.bindInstanceFields(this);
        currency.setItems(currenciesList);
        currency.setItemLabelGenerator(Currency::name);
        walletId.setItems(walletIdList);
        walletId.setItemLabelGenerator(Long -> walletClient.getWalletById(Long).toString());
        quantity.setValue(1d);
        quantity.setHasControls(true);
        quantity.setMin(0);
        quantity.setMax(1000);
        quantity.setStep(0.1);

        add(
                currency,
                quantity,
                walletId,
                createButtonsLayout()
        );
    }

    public void setWalletItem(WalletItemDto walletItemDto) {
        this.walletItemDto = walletItemDto;
        binder.readBean(walletItemDto);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, walletItemDto)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(walletItemDto);
            fireEvent(new SaveEvent(this, walletItemDto));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    // Events
    public static abstract class WalletItemFormEvent extends ComponentEvent<WalletItemForm> {
        private final WalletItemDto walletItemDto;

        protected WalletItemFormEvent(WalletItemForm source, WalletItemDto walletItemDto) {
            super(source, false);
            this.walletItemDto = walletItemDto;
        }

        public WalletItemDto getWalletItem() {
            return walletItemDto;
        }
    }

    public static class SaveEvent extends WalletItemFormEvent {
        SaveEvent(WalletItemForm source, WalletItemDto walletItemDto) {
            super(source, walletItemDto);
        }
    }

    public static class DeleteEvent extends WalletItemFormEvent {
        DeleteEvent(WalletItemForm source, WalletItemDto walletItemDto) {
            super(source, walletItemDto);
        }
    }

    public static class CloseEvent extends WalletItemFormEvent {
        CloseEvent(WalletItemForm source) {
            super(source, null);
        }
    }
}
