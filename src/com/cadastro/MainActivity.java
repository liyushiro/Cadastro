package com.cadastro;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	RegCad primeiroReg,registro,ultimoReg,auxiliar;
	EditText campoNome,campoEndereco,campoTelefone;
	int qtdRegistros,posicao;
	
	Button btnCadastro,btnMostra,btnCadastrar,btnVoltar,btnSair,btnProximo,
	btnAnterior;
	
	TextView mostraNome, mostraTelefone, mostraEndereco;
	
	 protected void mostraCxTexto(String msg, String titulo) {
			// Gera uma caixa de texto na tela com um
			// botão "OK"
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setMessage(msg);
			builder.setNeutralButton("OK", null);
			AlertDialog dialog = builder.create();
			dialog.setTitle(titulo);
			dialog.show(); 
	}
	 
	 void inicializaObjetos() {
			//Inicializa os objetos das views utilizando 
	 		//o try/catch para evitar force-close 

			//Objetos do layout principal
			try{
		          btnCadastro = (Button) findViewById(R.id.btnCadastro);
		          btnMostra = (Button) findViewById(R.id.btnMostrar);
			} catch (Exception e){
			   // Não faz nada
			}

			//objetos do layout de cadastramento
			try{
			   campoNome     = (EditText)findViewById(R.cad.nome);
			   campoEndereco = (EditText)findViewById(R.cad.endereco);
			   campoTelefone = (EditText)findViewById(R.cad.telefone);
			   btnCadastrar  = (Button)  findViewById(R.cadastro.btnCadastrar);
			   btnSair       = (Button)  findViewById(R.cadastro.btnSair);
			} catch (Exception e){
			   // Não faz nada
			}

			//objetos do layout de visualização de
	 	//registros 
			try{
	            	   mostraNome     = (TextView) findViewById(R.mostra.nome);
	            	   mostraTelefone = (TextView) findViewById(R.mostra.telefone);
	            	   mostraEndereco = (TextView) findViewById(R.mostra.endereco);
		               btnAnterior    = (Button)   findViewById(R.lista.btnAnterior);
		               btnProximo     = (Button)   findViewById(R.lista.btnProximo);
		               btnVoltar      = (Button)   findViewById(R.lista.btnVoltar);
			} catch (Exception e){
			   // Não faz nada
			}
		}


		void carregaListeners() {
			//inicializa os listeners dos objetos
	 		//utilizando o try/catch 
			//para evitar force-close da aplicação
			
			//listeners do layout principal
			try{
			  
				btnCadastro.setOnClickListener(new View.OnClickListener() {
			      public void onClick(View arg0) {
				iniciaCadastro();
			      }
			  });
			  btnMostra.setOnClickListener(new 
		 	    View.OnClickListener() {
			      public void onClick(View arg0) {
				iniciaMostrarLista();
			      }
			  });	
			} catch (Exception e){
		 	   // Não faz nada
			}

			//listeners do layout de cadastramento
			try{
			  btnCadastrar.setOnClickListener(new 
		 	    View.OnClickListener() {
			      public void onClick(View arg0) {
				try {
				  insereRegistro();
				  mostraCxTexto("Cadastro efetuado com sucesso", "Aviso");
			   	  campoEndereco.setText(null);
				  campoTelefone.setText(null);
				  campoNome.setText(null);
				  campoNome.requestFocus();
				} catch (Exception e) {
				  mostraCxTexto("Erro ao cadastrar", 
	 					  "Erro");
				}
			      }
			  });
			  btnSair.setOnClickListener(new 
	 		    View.OnClickListener() {
			      public void onClick(View arg0) {
				iniciaAplicacao();
			      }
			  });
			} catch (Exception e){
			   // Não faz nada
			}

			//listeners do layout de visualização de 
	 		//registros 
			try{
		         btnVoltar.setOnClickListener(new 
	 		   View.OnClickListener(){
		             public void onClick(View arg0){
		                 iniciaAplicacao();
		             }
	                });
		         btnAnterior.setOnClickListener(new 
	 		   View.OnClickListener(){
		             public void onClick(View arg0){
		            	 mostraRegAnterior();
		             }
		         });

		         btnProximo.setOnClickListener(new 
	 		   View.OnClickListener(){
		             public void onClick(View arg0){
		            	 mostraRegProximo();
		             }
		         });
			} catch (Exception e){
			   // Não faz nada
			}
		}

		void iniciaAplicacao() {
			//chama o layout principal
			setContentView(R.layout.main);
	        	inicializaObjetos();
	        	carregaListeners();
		}

		void iniciaCadastro() {
			//muda para o layout de cadastramento
			setContentView(R.layout.cadastro);
	        	inicializaObjetos();
	        	carregaListeners();
			campoNome.requestFocus();
		}

		void iniciaMostrarLista() {
			//verifica a existencia de registros e 
	  		//muda para o layout de visualização
	        	if(qtdRegistros==0) {
			  mostraCxTexto("Nenhum registro cadastrado", 
		 			  "Aviso");
	            	  iniciaAplicacao();
	            	  return;
	        	} else {
		          setContentView(R.layout.mostralista);
		          inicializaObjetos();
		          carregaListeners();
		        
		          posicao=1;
		          auxiliar=primeiroReg;
		          mostraRegistro();
	        	}
	    	}

		protected void mostraRegistro() {
			  //Mostra nos campos da tela os dados do registro
		          mostraNome.setText(auxiliar.nome);
		          mostraTelefone.setText(auxiliar.telefone);
		          mostraEndereco.setText(auxiliar.endereco);
			}

		protected void mostraRegProximo() {
	  	  //não faz nada se estiver no ultimo registro
		  if(posicao!=qtdRegistros) {
		      posicao++;
		      auxiliar=auxiliar.Prox;
		      mostraRegistro();
		  }
		}

		protected void mostraRegAnterior() {
	 	   //não faz nada se estiver no primeiro registro
	       if(posicao!=1) {  
	           posicao--;
	           auxiliar=auxiliar.Ant;
	           mostraRegistro();
	       }
		}

		protected void insereRegistro() {
			//Gera novo registro
			registro = new RegCad();
			//Pega dados digitados
			registro.nome =
	 			      campoNome.getText().toString();
			registro.endereco = 
	 			  campoEndereco.getText().toString();
			registro.telefone = 
		 		  campoTelefone.getText().toString();
			//Trata ordem dos registros
			if (primeiroReg == null)
				primeiroReg = registro;
				registro.Ant = ultimoReg;
			if (ultimoReg == null)
				ultimoReg = registro;
			else {
				ultimoReg.Prox = registro;
				ultimoReg = registro;
			}
			qtdRegistros++;
		}

	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qtdRegistros=0;
        primeiroReg = ultimoReg = null;
        iniciaAplicacao();
    }
}