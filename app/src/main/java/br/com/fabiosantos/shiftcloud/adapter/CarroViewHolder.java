package br.com.fabiosantos.shiftcloud.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.fabiosantos.shiftcloud.R;
import br.com.fabiosantos.shiftcloud.model.Carro;

/**
 * Created by alunoshift on 03/06/2017.
 */

public class CarroViewHolder extends RecyclerView.ViewHolder {

    public TextView tvModelo;
    public TextView tvFabricante;
    public Button btApagar;
    private List<Carro> carros;

    public CarroViewHolder(View itemView, final List<Carro> carros) {
        super(itemView);

        this.tvFabricante = (TextView) itemView.findViewById(R.id.tvFabricante);
        this.tvModelo = (TextView) itemView.findViewById(R.id.tvModelo);
        this.btApagar = (Button) itemView.findViewById(R.id.btApagar);

        this.carros = carros;

    }
}
