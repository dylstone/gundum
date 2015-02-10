package utils.parser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import play.mvc.With;

/**
 * 必須フォームアノテーション
 */
@With(RequireFormAction.class)
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireForm {
    /** 取得する予定のフォーム情報配列 */
    String[] value();
}
