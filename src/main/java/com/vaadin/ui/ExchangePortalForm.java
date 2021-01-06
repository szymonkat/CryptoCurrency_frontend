/*
package com.vaadin.ui;

import com.vaadin.domain.*;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Route(value = "exchangePortalForm", layout = MainLayout.class)
public class ExchangePortalForm extends FormLayout {

    ComboBox<String> provider = new ComboBox<>("Provider");
    ComboBox<Currency> currencyToBuy = new ComboBox<>("Currency to Buy");
    ComboBox<Currency> currencyToPay = new ComboBox<>("Currency to Pay");
    double ratio = 0.0;
    LocalDateTime localDateTime = LocalDateTime.now();
    Button save = new Button("Get Data");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<ExchangePortal> binder = new BeanValidationBinder<>(ExchangePortal.class);
    private ExchangePortal exchangePortal;


    public ExchangePortalForm(List<String> providerList, List<Currency> currenciesList, Currency usd) {
        addClassName("exchange-portal-form");
        binder.bindInstanceFields(this);
        currencyToBuy.setItems(currenciesList);
        currencyToBuy.setItemLabelGenerator(Currency::name);
        currencyToPay.setItems(usd);
        provider.setItems(providerList);
        provider.setItemLabelGenerator(String::toLowerCase);

        add(
                currencyToBuy,
                currencyToPay,
                provider,
                createButtonsLayout()
        );
    }

    public void setExchangePortal(ExchangePortal exchangePortal) {
        this.exchangePortal = exchangePortal;
        binder.readBean(exchangePortal);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, exchangePortal)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(exchangePortal);
            fireEvent(new SaveEvent(this, exchangePortal));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ExchangePortalFormEvent extends ComponentEvent<ExchangePortalForm> {
        private ExchangePortal exchangePortal;

        protected ExchangePortalFormEvent(ExchangePortalForm source, ExchangePortal exchangePortal) {
            super(source, false);
            this.exchangePortal = exchangePortal;
        }

        public ExchangePortal getExchangePortal() {
            return exchangePortal;
        }
    }

    public static class SaveEvent extends ExchangePortalFormEvent {
        SaveEvent(ExchangePortalForm source, ExchangePortal exchangePortal) {
            super(source, exchangePortal);
        }
    }

    public static class DeleteEvent extends ExchangePortalFormEvent {
        DeleteEvent(ExchangePortalForm source, ExchangePortal exchangePortal) {
            super(source, exchangePortal);
        }
    }

    public static class CloseEvent extends ExchangePortalFormEvent {
        CloseEvent(ExchangePortalForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
*/
