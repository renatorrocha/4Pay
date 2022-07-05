package com.example.appbanco.view.Home.HomeFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appbanco.R;
import com.example.appbanco.adapter.ExtratoAdapter;
import com.example.appbanco.databinding.FragmentHomeBinding;
import com.example.appbanco.help.GetMask;
import com.example.appbanco.model.Cartao;
import com.example.appbanco.model.ExtratoModel;
import com.example.appbanco.model.Notificacao;
import com.example.appbanco.model.Usuario;
import com.example.appbanco.view.ChatBot.ChatBot;
import com.example.appbanco.view.Crypto.Crypto;
import com.example.appbanco.view.Home.Notificacoes;
import com.example.appbanco.view.Home.Seguros;
import com.example.appbanco.view.Pagamentos.Cartoes.CartaoFatura;
import com.example.appbanco.view.Pagamentos.Cartoes.Cartoes;
import com.example.appbanco.view.Pagamentos.Cartoes.GerarCartoes;
import com.example.appbanco.view.Pagamentos.Deposito.DepositofFormActivity;
import com.example.appbanco.view.Pagamentos.Pagamento.Pagamento;
import com.example.appbanco.view.Pagamentos.Pix.Pix;
import com.example.appbanco.view.Pagamentos.Recarga.RecargaInicio;
import com.example.appbanco.viewModel.GetUserViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<Cartao> cartaoList = new ArrayList<>();
    private List<Notificacao> notiList = new ArrayList<>();
    private List<ExtratoModel> extratoList = new ArrayList<>();
    private ExtratoAdapter extratoAdapter;
    private Usuario usuario;
    private Cartao cartaoUm, cartaoDois, cartaoTres;
    private GetUserViewModel userViewModel;
    private BottomSheetBehavior bottomSheetBehavior;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        userViewModel = new ViewModelProvider(getActivity()).get(GetUserViewModel.class);
        userViewModel.verifyUserData();
        userViewModel.verifyNoti();
        userViewModel.verifyCartao();
        userViewModel.verifyExtrato();

        userViewModel.getUser.observe(getViewLifecycleOwner(), sucess -> {
            if (sucess) {
                usuario = userViewModel.getUser();
                if (usuario != null) {
                    binding.tvSaldoValor.setText(view.getContext().getString(R.string.txt_valor_saldo, GetMask.getValor(usuario.getSaldo())));
                    String[] splitName = usuario.getNome().trim().split("\\s+");
                    binding.tvBemVindo.setText("Olá, " + splitName[0]);
                    if (usuario.getUrlImagem() != null) {
                        Picasso.get().load(usuario.getUrlImagem())
                                .into(binding.ivUserFoto);
                    }
                }

            }
        });

        userViewModel.getNoti.observe(getViewLifecycleOwner(), sucess -> {
            if (sucess) {
                notiList = userViewModel.getNoti();

                if (notiList.isEmpty()) {
                    binding.tvNoti.setText("0");
                    binding.tvNoti.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvNoti.setText(String.valueOf(notiList.size()));
                    binding.tvNoti.setVisibility(View.VISIBLE);

                }
            }

        });

        userViewModel.getCartao.observe(getViewLifecycleOwner(), sucess -> {
            if (sucess) {
                cartaoList = userViewModel.getCartao();

                if (cartaoList.size() > 0) {
                    verificarCartoes(view);
                } else {
                    binding.clCartao.setBackgroundResource(R.drawable.ic_gerar_cartao);
                    binding.tvTipoCartao.setText("");
                    binding.tvValidadeCartao.setText("");
                    binding.tvNumCartao.setText("");

                    binding.clListaCartoes.removeView(binding.clCartaoDois);
                    binding.clListaCartoes.removeView(binding.clCartaoTres);
                }
            }
        });

        userViewModel.getExtrato.observe(getViewLifecycleOwner(), sucess -> {
            if (sucess) {
                extratoList = userViewModel.getExtrato();


                binding.rvExtrato.setLayoutManager(new LinearLayoutManager(view.getContext()));
                binding.rvExtrato.setHasFixedSize(true);
                extratoAdapter = new ExtratoAdapter(extratoList, view.getContext());
                binding.rvExtrato.setAdapter(extratoAdapter);
            }
        });

        clicks(view);

        ConstraintLayout clBehavior = view.findViewById(R.id.clBehavior);
        bottomSheetBehavior = BottomSheetBehavior.from(clBehavior);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        return view;
    }

    private void clicks(View view) {
        binding.clCartoes.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Cartoes.class));
        });

        binding.clDeposito.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), DepositofFormActivity.class));
        });

        binding.clRecarga.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), RecargaInicio.class));
        });

        binding.clSeguro.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Seguros.class));
        });

        binding.clPix.setOnClickListener(view1 -> {
            Intent itPix = new Intent(getContext(), Pix.class);
            itPix.putExtra("userSaldo", binding.tvSaldoValor.getText().toString());
            startActivity(itPix);
        });

        binding.ivNoti.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Notificacoes.class));
        });

        binding.clAtendimento.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), ChatBot.class));
        });

        binding.clPags.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Pagamento.class));
        });

        binding.clCartao.setOnClickListener(view1 -> {
            if (cartaoList.size() < 1) {
                startActivity(new Intent(view.getContext(), GerarCartoes.class));
            } else {
                Intent it = new Intent(view.getContext(), CartaoFatura.class);
                it.putExtra("cartao", cartaoUm);
                startActivity(it);
            }
        });

        binding.clCartaoDois.setOnClickListener(view1 -> {
            Intent it = new Intent(view.getContext(), CartaoFatura.class);
            it.putExtra("cartao", cartaoDois);
            startActivity(it);
        });

        binding.clCartaoTres.setOnClickListener(view1 -> {
            Intent it = new Intent(view.getContext(), CartaoFatura.class);
            it.putExtra("cartao", cartaoTres);
            startActivity(it);
        });

        binding.clCrypto.setOnClickListener(view1 -> {
            startActivity(new Intent(view.getContext(), Crypto.class));
        });

        binding.ivEsconderSaldo.setOnClickListener(view1 -> {
            esconderSaldo(view);
        });
    }

    private void esconderSaldo(View view) {
        if (binding.ivEsconderSaldo.getDrawable().getConstantState() == ContextCompat.getDrawable(getContext(), R.drawable.ic_eye).getConstantState()) {
            binding.ivEsconderSaldo.setImageResource(R.drawable.ic_eye_closed);
            binding.tvSaldoValor.setText("***");

            if (cartaoList.size() == 1) {
                binding.tvNumCartao.setText("**** **** **** ****");
                binding.tvValidadeCartao.setText("**/**");

            } else if (cartaoList.size() == 2) {
                binding.tvNumCartao.setText("**** **** **** ****");
                binding.tvValidadeCartao.setText("**/**");

                binding.tvNumCartaoDois.setText("**** **** **** ****");
                binding.tvValidadeCartaoDois.setText("**/**");

            } else if (cartaoList.size() == 3) {
                binding.tvNumCartao.setText("**** **** **** ****");
                binding.tvValidadeCartao.setText("**/**");

                binding.tvNumCartaoDois.setText("**** **** **** ****");
                binding.tvValidadeCartaoDois.setText("**/**");

                binding.tvNumCartaoTres.setText("**** **** **** ****");
                binding.tvValidadeCartaoTres.setText("**/**");

            }

        } else {
            binding.ivEsconderSaldo.setImageResource(R.drawable.ic_eye);
            binding.tvSaldoValor.setText(view.getContext().getString(R.string.txt_valor_saldo, GetMask.getValor(usuario.getSaldo())));
            verificarCartoes(view);
        }
    }

    private void verificarCartoes(View view) {

        if (cartaoList.size() == 1) {
            cartaoUm = cartaoList.get(0);
            binding.clCartao.setBackgroundResource(R.drawable.credit_card);
            binding.tvTipoCartao.setText(cartaoList.get(0).getTipo());
            String[] splitNumero = cartaoList.get(0).getNumeros().trim().split(" ");
            binding.tvNumCartao.setText(view.getContext().getString(R.string.txt_codigo_cartao_put, splitNumero[3]));
            binding.tvValidadeCartao.setText(cartaoList.get(0).getDataVencimento());

            binding.clListaCartoes.removeView(binding.clCartaoDois);
            binding.clListaCartoes.removeView(binding.clCartaoTres);


        } else if (cartaoList.size() == 2) {
            cartaoUm = cartaoList.get(0);
            cartaoDois = cartaoList.get(1);

            binding.tvTipoCartao.setText(cartaoList.get(0).getTipo());
            String[] splitNumero = cartaoList.get(0).getNumeros().trim().split(" ");
            binding.tvNumCartao.setText(view.getContext().getString(R.string.txt_codigo_cartao_put, splitNumero[3]));
            binding.tvValidadeCartao.setText(cartaoList.get(0).getDataVencimento());

            binding.tvTipoCartaoDois.setText(cartaoList.get(1).getTipo());
            String[] splitNumero2 = cartaoList.get(1).getNumeros().trim().split(" ");
            binding.tvNumCartaoDois.setText(view.getContext().getString(R.string.txt_codigo_cartao_put, splitNumero2[3]));
            binding.tvValidadeCartaoDois.setText(cartaoList.get(1).getDataVencimento());


            binding.clListaCartoes.removeView(binding.clCartaoTres);
        } else if (cartaoList.size() == 3) {
            cartaoUm = cartaoList.get(0);
            cartaoDois = cartaoList.get(1);
            cartaoTres = cartaoList.get(2);

            binding.clCartao.setBackgroundResource(R.drawable.credit_card);
            binding.tvTipoCartao.setText(cartaoList.get(0).getTipo());
            String[] splitNumero = cartaoList.get(0).getNumeros().trim().split(" ");
            binding.tvNumCartao.setText(view.getContext().getString(R.string.txt_codigo_cartao_put, splitNumero[3]));
            binding.tvValidadeCartao.setText(cartaoList.get(0).getDataVencimento());

            binding.clCartaoDois.setBackgroundResource(R.drawable.credit_card);
            binding.tvTipoCartaoDois.setText(cartaoList.get(1).getTipo());
            String[] splitNumero2 = cartaoList.get(1).getNumeros().trim().split(" ");
            binding.tvNumCartaoDois.setText(view.getContext().getString(R.string.txt_codigo_cartao_put, splitNumero2[3]));
            binding.tvValidadeCartaoDois.setText(cartaoList.get(1).getDataVencimento());

            binding.clCartaoTres.setBackgroundResource(R.drawable.credit_card);
            binding.tvTipoCartaoTres.setText(cartaoList.get(2).getTipo());
            String[] splitNumero3 = cartaoList.get(2).getNumeros().trim().split(" ");
            binding.tvNumCartaoTres.setText(view.getContext().getString(R.string.txt_codigo_cartao_put, splitNumero3[3]));
            binding.tvValidadeCartaoTres.setText(cartaoList.get(2).getDataVencimento());

        }


    }

}