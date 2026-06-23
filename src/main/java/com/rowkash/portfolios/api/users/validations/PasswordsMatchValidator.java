package com.rowkash.portfolios.api.users.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

  private static final String PASSWORD_FIELD = "password";
  private static final String CONFIRM_PASSWORD_FIELD = "confirmPassword";

  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context) {
    try {
      // Получаем значения полей через рефлексию
      var passwordMethod = obj.getClass().getMethod(PASSWORD_FIELD);
      var confirmPasswordMethod = obj.getClass().getMethod(CONFIRM_PASSWORD_FIELD);

      String password = (String) passwordMethod.invoke(obj);
      String confirmPassword = (String) confirmPasswordMethod.invoke(obj);

      // Если хотя бы одно поле null — пусть @NotBlank отработает
      if (password == null || confirmPassword == null) {
        return true;
      }

      return password.equals(confirmPassword);

    } catch (Exception e) {
      // Если поля не найдены — валидация не пройдена (или можно бросить исключение)
      return false;
    }
  }
}
