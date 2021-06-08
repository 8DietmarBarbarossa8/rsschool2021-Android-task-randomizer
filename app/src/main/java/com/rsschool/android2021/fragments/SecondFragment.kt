package com.rsschool.android2021.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rsschool.android2021.R
import com.rsschool.android2021.interfaces.IBackActivity
import com.rsschool.android2021.interfaces.IFragmentsActions

class SecondFragment : Fragment(),
    IBackActivity {
    private var backButton: Button? = null
    private var resultText: TextView? = null
    private var result = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultText = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        val random = generate(min, max)
        resultText?.text = random.toString()

        backButton?.setOnClickListener {
            clickBack()
        }
    }

    private fun generate(min: Int, max: Int): Int = (min..max).random()

    private fun clickBack(){
        result = resultText?.text.toString().toInt()
        (activity as IFragmentsActions).sendPreviousNumber(result)
    }

    override fun isMayBackPrevious(): Boolean {
        clickBack()
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()

            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args

            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}