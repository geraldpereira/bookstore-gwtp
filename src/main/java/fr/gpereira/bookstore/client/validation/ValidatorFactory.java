package fr.gpereira.bookstore.client.validation;

import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

import fr.gpereira.bookstore.model.Book;
import fr.gpereira.bookstore.model.validation.CreateChecks;
import fr.gpereira.bookstore.model.validation.UpdateChecks;

public final class ValidatorFactory extends AbstractGwtValidatorFactory {

	/**
	 * Validator marker for the Validation Sample project. Only the classes and
	 * groups listed in the {@link GwtValidation} annotation can be validated.
	 */
	@GwtValidation(value={Book.class}, groups={CreateChecks.class, UpdateChecks.class})
	public interface GwtValidator extends Validator {
	}

	@Override
	public AbstractGwtValidator createValidator() {
		return GWT.create(GwtValidator.class);
	}

}