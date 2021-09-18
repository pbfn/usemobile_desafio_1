package com.example.perguntas_e_respostas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.perguntas_e_respostas.databinding.FragmentGameBinding


class GameFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var qtd_perguntas: Int? = null
    private var qtd_acertos: Int = 0
    private val resp: Boolean? = null
    private var questions = IntArray(6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateQuestions()
        setClicksEvents()
        getArgs()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            //marcar a alteranativa em azul
            R.id.radio_button_resp_1 -> {
                binding.buttonResp.isEnabled = true
            }
            R.id.radio_button_resp_2 -> {
                binding.buttonResp.isEnabled = true
            }
            R.id.radio_button_resp_3 -> {
                binding.buttonResp.isEnabled = true
            }
            R.id.radio_button_resp_4 -> {
                binding.buttonResp.isEnabled = true
            }

            R.id.button_resp -> {
                if (binding.buttonResp.text.equals("Responder")) {
                    binding.buttonResp.text = "Próxima pergunta"
                    //TODO Verificar se é a resposta correta e alterar a variável resp para true
                    desativeRadio()


                } else {
                    nextFragment()
                }
            }

        }
    }

    //Eventos de Click
    private fun setClicksEvents() {
        binding.buttonResp?.setOnClickListener(this)
        binding.radioButtonResp1?.setOnClickListener(this)
        binding.radioButtonResp2?.setOnClickListener(this)
        binding.radioButtonResp3?.setOnClickListener(this)
        binding.radioButtonResp4?.setOnClickListener(this)
    }

    //Desativando os radio buttons para evitar de alterar a resposta após responder
    private fun desativeRadio() {
        binding.radioButtonResp1.isEnabled = false
        binding.radioButtonResp2.isEnabled = false
        binding.radioButtonResp3.isEnabled = false
        binding.radioButtonResp4.isEnabled = false
    }

    //Varificando para qual fragment deve ir
    private fun nextFragment() {
        if (qtd_perguntas!! < 10) {
            if(resp == true){
                qtd_acertos = qtd_acertos!! + 1
            }
            qtd_perguntas = qtd_perguntas!! + 1
            val direction = GameFragmentDirections.actionGameFragmentSelf(qtd_perguntas!!, qtd_acertos!!)
            findNavController().navigate(direction)

        } else {
            val direction = GameFragmentDirections.actionGameFragmentToResultFragment(qtd_acertos!!)
            findNavController().navigate(direction)
        }
    }

    //Pegando argumentos da tela anterior
    private fun getArgs() {
        val args: GameFragmentArgs by navArgs()
        qtd_perguntas = args.quantidadePerguntas
        binding.textViewPergunta?.text = "Pergunta nº" + qtd_perguntas.toString() +": qual a soma de "+questions[0].toString() + "  " +questions[1].toString()
    }


    private fun generateQuestions(){
        questions[0] = (1..400).random()
        questions[1] = (1..400).random()
        questions[2] = (1..400).random()
        questions[3] = (1..400).random()
        questions[4] = (1..400).random()
        questions[5] = questions[0] + questions[1]
    }

}