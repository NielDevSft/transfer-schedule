package com.transferschedule.api.models;

public enum TIPOOPERACAO {
    A(1), B(2), C(3), D(4);
    public final int valorOperacao;
    TIPOOPERACAO(int valor) {
        valorOperacao = valor;
    }
}
