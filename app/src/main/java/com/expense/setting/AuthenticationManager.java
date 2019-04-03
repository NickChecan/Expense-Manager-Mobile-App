package com.expense.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.expense.model.TokenResponse;
import com.google.gson.Gson;

import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class AuthenticationManager {

    private SharedPreferences preferences;

    private TokenResponse token;

    private Gson gson;

    public AuthenticationManager(Context context) {
        this.gson = new Gson();
        this.preferences = context.getSharedPreferences("expense", MODE_PRIVATE);
        String tokenJson = this.preferences.getString("Token", "");
        this.token = this.gson.fromJson(tokenJson, TokenResponse.class);
    }

    public TokenResponse getToken() {
        return this.token;
    }

    public void setToken(TokenResponse newToken) {
        String tokenJson = this.gson.toJson(newToken);
        this.preferences.edit().putString("Token", tokenJson).apply();
        this.token = newToken;
    }

    public boolean isValid() {
        if (this.token == null) return false;
        this.token.setExpiresIn(this.token.getExpiresIn());
        return this.token.getExpirationTime().after(new Date());
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

}
