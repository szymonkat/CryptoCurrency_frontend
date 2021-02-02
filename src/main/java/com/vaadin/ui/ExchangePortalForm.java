package com.vaadin.ui;

import com.vaadin.domain.Currency;
import com.vaadin.dto.ExchangePortalDto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import java.util.List;

@Route(value = "exchangePortalForm", layout = MainLayout.class)
public class ExchangePortalForm extends FormLayout {

    ComboBox<String> provider = new ComboBox<>("Provider");
    ComboBox<Currency> currencyToBuy = new ComboBox<>("Currency to Buy");
    ComboBox<Currency> currencyToPay = new ComboBox<>("Currency to Pay");
    //    double ratio = 0.0;
//    LocalDateTime localDateTime = LocalDateTime.now();
    Button save = new Button("Get Data");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<ExchangePortalDto> binder = new BeanValidationBinder<>(ExchangePortalDto.class);
    private ExchangePortalDto exchangePortalDto;


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

    public void setExchangePortal(ExchangePortalDto exchangePortalDto) {
        this.exchangePortalDto = exchangePortalDto;
        binder.readBean(exchangePortalDto);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, exchangePortalDto)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(exchangePortalDto);
            fireEvent(new SaveEvent(this, exchangePortalDto));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    // Events
    public static abstract class ExchangePortalFormEvent extends ComponentEvent<ExchangePortalForm> {
        private final ExchangePortalDto exchangePortalDto;

        protected ExchangePortalFormEvent(ExchangePortalForm source, ExchangePortalDto exchangePortalDto) {
            super(source, false);
            this.exchangePortalDto = exchangePortalDto;
        }

        public ExchangePortalDto getExchangePortal() {
            return exchangePortalDto;
        }
    }

    public static class SaveEvent extends ExchangePortalFormEvent {
        SaveEvent(ExchangePortalForm source, ExchangePortalDto exchangePortalDto) {
            super(source, exchangePortalDto);
        }
    }

    public static class DeleteEvent extends ExchangePortalFormEvent {
        DeleteEvent(ExchangePortalForm source, ExchangePortalDto exchangePortalDto) {
            super(source, exchangePortalDto);
        }
    }

    public static class CloseEvent extends ExchangePortalFormEvent {
        CloseEvent(ExchangePortalForm source) {
            super(source, null);
        }
    }
}
