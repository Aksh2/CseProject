// Generated code from Butter Knife. Do not modify!
package project.cse.anti;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends project.cse.anti.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624092, "field '_phoneText'");
    target._phoneText = finder.castView(view, 2131624092, "field '_phoneText'");
    view = finder.findRequiredView(source, 2131624093, "field '_passwordText'");
    target._passwordText = finder.castView(view, 2131624093, "field '_passwordText'");
    view = finder.findRequiredView(source, 2131624094, "field '_loginButton'");
    target._loginButton = finder.castView(view, 2131624094, "field '_loginButton'");
    view = finder.findRequiredView(source, 2131624095, "field '_signupLink'");
    target._signupLink = finder.castView(view, 2131624095, "field '_signupLink'");
  }

  @Override public void unbind(T target) {
    target._phoneText = null;
    target._passwordText = null;
    target._loginButton = null;
    target._signupLink = null;
  }
}
