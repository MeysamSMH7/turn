package com.example.turn.Classes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.turn.R;
import com.google.android.material.snackbar.Snackbar;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ShowMessage {

    private Context context;
    private String value;
    private Activity activity;
    private String textTitle;
    private String textBody;
    private String textButtonNO;
    private String textButtonOK;
    private String job;
    private boolean btnButtonNO;
    private boolean btnButtonOK;

    private Runnable runFadIn = null;
    private Runnable runFadOut = null;

    private String textEditText = "";

    public ShowMessage(Context context) {
        this.context = context;
    }


    //    1 = success   2 = waring/asking   3 = error
    public void ShowMessType2(String textTitle, String textBody, String textButtonNO,
                              String textButtonOK, boolean btnButtonOK, boolean btnButtonNO, final String job,
                              String value, int status, Activity activity, boolean cancelable) {
        try {


            this.value = value;
            this.activity = activity;
            this.textTitle = textTitle;
            this.textBody = textBody;
            this.textButtonNO = textButtonNO;
            this.textButtonOK = textButtonOK;
            this.btnButtonNO = btnButtonNO;
            this.btnButtonOK = btnButtonOK;
            this.job = job;

            final PrettyDialog prettyDialog = new PrettyDialog(context);

            prettyDialog.setCancelable(cancelable);

            if (status == 1) {

                prettyDialog.setIcon(
                        R.drawable.icon_ok_green,
                        null,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            } else if (status == 2) {
                prettyDialog.setIcon(
                        R.drawable.icon_warning,
                        null,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            } else if (status == 3) {
                prettyDialog.setIcon(
                        R.drawable.icon_error_red,
                        null,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            } else {

                prettyDialog.setIcon(
                        R.drawable.pdlg_icon_info,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            }

            if (btnButtonOK)
                prettyDialog.addButton(
                        textButtonOK,
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_green,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
//                                doJob(job);
                            }
                        }
                );

            if (btnButtonNO)
                prettyDialog.addButton(
                        textButtonNO,
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
//                                doJob(job);
                            }
                        }
                );

//        prettyDialog.addButton(
//                "Option 3",
//                R.color.pdlg_color_black,
//                R.color.pdlg_color_gray,
//                null
//        );


            prettyDialog.setTitle(textTitle);
            prettyDialog.setMessage(textBody);
            prettyDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ShowMessType2_NoBtn_2(String textBody, boolean btn, int status, String text) {
        try {

            final PrettyDialog prettyDialog = new PrettyDialog(context);

            if (status == 1) {

                prettyDialog.setIcon(
                        R.drawable.icon_ok_green,
                        null,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            } else if (status == 2) {
                prettyDialog.setIcon(
                        R.drawable.icon_warning,
                        null,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            } else if (status == 3) {
                prettyDialog.setIcon(
                        R.drawable.icon_error_red,
                        null,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            } else {

                prettyDialog.setIcon(
                        R.drawable.pdlg_icon_info,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            }
            if (btn)
                prettyDialog.addButton(
                        text,
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                );

//        prettyDialog.addButton(
//                "Option 3",
//                R.color.pdlg_color_black,
//                R.color.pdlg_color_gray,
//                null
//        );

            prettyDialog.setMessage(textBody);
            prettyDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void ShowMessType2_NoBtn(String textBody, boolean btn, int setIcon) {
        try {

            final PrettyDialog prettyDialog = new PrettyDialog(context);

            if (setIcon == 1) {
                prettyDialog.setIcon(
                        R.drawable.icon_ok_green,
                        null,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            } else if (setIcon == 2) {
                prettyDialog.setIcon(
                        R.drawable.icon_warning,
                        null,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            } else if (setIcon == 3) {
                prettyDialog.setIcon(
                        R.drawable.icon_error_red,
                        null,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                            }
                        });
            }

            if (btn)
                prettyDialog.addButton(
                        "بستن",
                        R.color.pdlg_color_white,
                        R.color.colorPrimaryDark,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                );

//        prettyDialog.addButton(
//                "Option 3",
//                R.color.pdlg_color_black,
//                R.color.pdlg_color_gray,
//                null
//        );

            prettyDialog.setMessage(textBody);
            prettyDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ShowMessType2_NetError() {
        try {

            final PrettyDialog prettyDialog = new PrettyDialog(context);

            prettyDialog.setIcon(
                    R.drawable.icon_warning,
                    null,
                    new PrettyDialogCallback() {
                        @Override
                        public void onClick() {

                        }
                    });
            prettyDialog.addButton(
                    "خُب!",
                    R.color.pdlg_color_white,
                    R.color.pdlg_color_red,
                    new PrettyDialogCallback() {
                        @Override
                        public void onClick() {
                            prettyDialog.dismiss();
                        }
                    }
            );

            prettyDialog.setTitle(context.getString(R.string.AlertCantConnect));
            prettyDialog.setMessage(context.getString(R.string.AlertCheckNetAgain));
            prettyDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ShowMessage_SnackBar(Object layout, String text) {
        try {

            Snackbar snackbar = Snackbar.make((View) layout, text, Snackbar.LENGTH_LONG).setAction("بستن", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            View sb = snackbar.getView();
            TextView tv = sb.findViewById(com.google.android.material.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            sb.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            snackbar.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
