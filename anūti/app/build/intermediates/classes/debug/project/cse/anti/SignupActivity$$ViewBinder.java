// Generated code from Butter Knife. Do not modify!
package project.cse.anti;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SignupActivity$$ViewBinder<T extends project.cse.anti.SignupActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624101, "field '_nameText'");
    target._nameText = finder.castView(view, 2131624101, "field '_nameText'");
    view = finder.findRequiredView(source, 2131624092, "field '_phoneText'");
    target._phoneText = finder.castView(view, 2131624092, "field '_phoneText'");
    view = finder.findRequiredView(source, 2131624102, "field '_emailText'");
    target._emailText = finder.castView(view, 2131624102, "field '_emailText'");
    view = finder.findRequiredView(source, 2131624093, "field '_passwordText'");
    target._passwordText = finder.castView(view, 2131624093, "field '_passwordText'");
    view = finder.findRequiredView(source, 2131624103, "field '_signupButton'");
    target._signupButton = finder.castView(view, 2131624103, "field '_signupButton'");
    view = finder.findRequiredView(source, 2131624104, "field '_loginLink'");
    target._loginLink = finder.castView(view, 2131624104, "field '_loginLink'");
  }

  @Override public void unbind(T target) {
    target._nameText = null;
    target._phoneText = null;
    target._emailText = null;
    target._passwordText = null;
    target._signupButton = null;
    target._loginLink = null;
  }
}
