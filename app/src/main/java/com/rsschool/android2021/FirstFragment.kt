package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment(), IBackActivity {
    private lateinit var generateButton: Button
    private lateinit var previousResult: TextView

    private lateinit var minValue: EditText
    private lateinit var maxValue: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        minValue = view.findViewById(R.id.min_value) as EditText
        maxValue = view.findViewById(R.id.max_value) as EditText

        val scoreInfo = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${scoreInfo.toString()}"

        generateButton?.setOnClickListener {
            if (minValue.length() != 0 && maxValue.length() != 0){
                val min: Int? = processHugeNumber(minValue.text.toString())
                val max: Int? = processHugeNumber(maxValue.text.toString())
                if (min != null && max != null){
                    if (max >= min)
                        (activity as IFragmentsActions).sendMinAndMaxValues(min, max)
                    else
                        Toast.makeText(context, R.string.compareMessage, Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(context, R.string.numberOutOfBounds, Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(context, R.string.emptyMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun processHugeNumber(hugeNumber: String): Int? =
        try { hugeNumber.toInt() } catch (e: NumberFormatException) { null }

    override fun isMayBackPrevious(): Boolean = false

    companion object {
        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()

            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args

            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}