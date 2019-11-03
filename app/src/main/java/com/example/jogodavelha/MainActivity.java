package com.example.jogodavelha;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button [] [] buttons = new Button[3][3];   //Indica que são 3 linhas e 3 colunas

    private boolean rodada1Jogador = true;

    private int contagemDeRodadas;
    private int vitorias1Jogador;
    private int vitorias2Jogador;

    private TextView textViewJogador1;
    private TextView textViewJogador2;
    private Button botaoReset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Jogo da Velha");

        textViewJogador1 = findViewById(R.id.tv_jogador1);
        textViewJogador2 = findViewById(R.id.tv_jogador2);

        for(int i =0;i <3;i++){
            for(int j =0;j<3;j++){
                String buttonID = "btn_"+i+j;
                int resID = getResources().getIdentifier(buttonID,"id", getPackageName());
                buttons [i][j] = findViewById(resID);
                buttons [i][j].setOnClickListener(this);
                }
        }

        botaoReset = findViewById(R.id.btn_reset);
        botaoReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetarJogo();
            }

            private void ResetarJogo() {
               vitorias1Jogador =0;
               vitorias2Jogador = 0;
                atualizarPontos();
                resetarJogo();
            }
        });
    }

    @Override
    public void onClick(View v) {
            if(!((Button) v).getText().toString().equals("")){
                return;
            }

            if(rodada1Jogador){
                ((Button) v).setText("X");  //Se for o primeiro jogador marca X
            }else{
                ((Button) v).setText("O"); //Se for o segundo jogador marca O
            }

            contagemDeRodadas++;


            if(validarVitoria()){
                if(rodada1Jogador){
                    jogador1Vence();
                }else{
                    jogador2Vence();
                }
            }else if (contagemDeRodadas == 9){
                Empate();
        }else{
                rodada1Jogador = !rodada1Jogador;
            }

    }

    private void Empate() {
        AlertDialog.Builder alertaEmpate = new AlertDialog.Builder(MainActivity.this);
        alertaEmpate.setTitle("Empate!");
        alertaEmpate.setMessage("O jogo empatou!");
        alertaEmpate.setIcon(R.drawable.ic_empate);
        alertaEmpate.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertaEmpate.create().show();
        resetarJogo();
    }
    private void jogador1Vence() {
        vitorias1Jogador++;
        atualizarPontos();
        resetarJogo();
        AlertDialog.Builder alertaJogador1Venceu = new AlertDialog.Builder(MainActivity.this);
        alertaJogador1Venceu.setTitle("Jogador 1 venceu!");
        alertaJogador1Venceu.setMessage("Jogador 1 venceu!");
        alertaJogador1Venceu.setIcon(R.drawable.ic_vitoria);
        alertaJogador1Venceu.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertaJogador1Venceu.create().show();

    }

    private void jogador2Vence() {
        vitorias2Jogador++;
        atualizarPontos();
        resetarJogo();
        AlertDialog.Builder alertaJogador2Venceu = new AlertDialog.Builder(MainActivity.this);
        alertaJogador2Venceu.setTitle("Jogador 2 venceu!");
        alertaJogador2Venceu.setMessage("Jogador 2 venceu!");
        alertaJogador2Venceu.setIcon(R.drawable.ic_vitoria);
        alertaJogador2Venceu.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertaJogador2Venceu.create().show();
    }

    private void resetarJogo() {
            for(int i = 0;i<3;i++){
                for(int j = 0;j<3;j++){
                    buttons [i][j].setText("");
                }

                contagemDeRodadas = 0;
                rodada1Jogador = true;
            }
    }

    private void atualizarPontos() {
        textViewJogador1.setText("Jogador 1: "+ vitorias1Jogador);
        textViewJogador2.setText("Jogador 2: "+vitorias2Jogador);
    }


    private boolean validarVitoria(){
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {           //Verifica Linha
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {             //Verifica Coluna
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {              //Verifica Diagonal
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])          ////Verifica Diagonal
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

 }

