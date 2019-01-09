package com.garren.roomate.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.garren.roomate.R;
import com.garren.roomate.activities.RoomActivity;
import com.garren.roomate.models.Room;

public class SecretPasswordDialogFragment extends DialogFragment {
    private Room mRoom;
    private EditText secretPassword;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_password, null);
        secretPassword = dialogView.findViewById(R.id.secret_password);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        validatePassword(secretPassword.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SecretPasswordDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void setRoom(Room room) {
        this.mRoom = room;
    }

    private void validatePassword(String attempt) {
        if(attempt.equals(mRoom.getPassword())) {
            proceedToRoom();
        } else {
            Toast.makeText(getActivity(), "Invalid Password!", Toast.LENGTH_LONG).show();
        }
    }

    private void proceedToRoom() {
        Intent intent = new Intent(getActivity(), RoomActivity.class);
        intent.putExtra("ROOM_ID", mRoom.getKey());
        startActivity(intent);
    }
}
