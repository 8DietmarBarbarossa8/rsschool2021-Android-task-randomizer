package com.rsschool.android2021.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.rsschool.android2021.R
import com.rsschool.android2021.interfaces.IBackActivity
import com.rsschool.android2021.interfaces.IFragmentsActions

class ThirdFragment : Fragment(),
    IBackActivity {
    private lateinit var resultsList: TextView
    private lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultsList = view.findViewById(R.id.results_list)
        button = view.findViewById(R.id.back2)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0
        val count = arguments?.getInt(COUNT_OF_RESULT_KEY) ?: 0

        val array = (min..max).toList().toIntArray()
        array.shuffle()

        var resultOnScreen = ""
        for (i in 0 until count)
            resultOnScreen += "[${i + 1}] - ${array[i]}\n"

        resultsList.text = resultOnScreen

        button.setOnClickListener {
            (activity as IFragmentsActions).sendPreviousNumber(0)
        }
    }

    override fun isMayBackPrevious(): Boolean {
        (activity as IFragmentsActions).sendPreviousNumber(0)
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance(min: Int, max: Int, count: Int): ThirdFragment {
            val fragment = ThirdFragment()
            val args = Bundle()

            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            args.putInt(COUNT_OF_RESULT_KEY, count)
            fragment.arguments = args

            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
        private const val COUNT_OF_RESULT_KEY = "COUNT_RESULT"
    }
}