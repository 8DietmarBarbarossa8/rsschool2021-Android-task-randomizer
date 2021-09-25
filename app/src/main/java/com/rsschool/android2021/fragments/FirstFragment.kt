package com.rsschool.android2021.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.rsschool.android2021.R
import com.rsschool.android2021.interfaces.IBackActivity
import com.rsschool.android2021.interfaces.IFragmentsActions

class FirstFragment : Fragment(),
    IBackActivity {
    private lateinit var generateButton: Button
    private lateinit var previousResult: TextView

    private lateinit var minValue: EditText
    private lateinit var maxValue: EditText
    private lateinit var countResult: EditText

    private lateinit var setResultCheckBox: CheckBox
    private var isVisiblyCountResultEditText: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        setResultCheckBox = view.findViewById(R.id.SetResultCheckBox)

        minValue = view.findViewById(R.id.min_value) as EditText
        maxValue = view.findViewById(R.id.max_value) as EditText

        val scoreInfo = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult.text = "Previous result: ${scoreInfo.toString()}"

        countResult = view.findViewById(R.id.count_result)

        generateButton.setOnClickListener {
            if (minValue.length() != 0 && maxValue.length() != 0 &&
                (countResult.length() != 0 || !isVisiblyCountResultEditText)){
                val min: Int? = minValue.text.toString().toIntOrNull()
                val max: Int? = maxValue.text.toString().toIntOrNull()
                val count: Int? = if (!isVisiblyCountResultEditText) -1 else
                    countResult.text.toString().toIntOrNull()
                if (min != null && max != null && count != null){
                    if (max >= min){
                        if (!isVisiblyCountResultEditText)
                            (activity as IFragmentsActions).sendMinAndMaxValues(min, max)
                        else
                            if (count <= (max - min + 1)){
                                if (count in 2..1000)
                                    (activity as IFragmentsActions)
                                        .sendCountOfResults(min, max, count)
                                else
                                    Toast.makeText(context, R.string.outCountValueMessage,
                                        Toast.LENGTH_SHORT).show()
                            } else
                                Toast.makeText(context, R.string.rangeEntryMessage,
                                    Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(context, R.string.compareMessage, Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(context, R.string.numberOutOfBounds, Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(context, R.string.emptyMessage, Toast.LENGTH_SHORT).show()
        }

        setResultCheckBox.setOnClickListener {
            isVisiblyCountResultEditText = !isVisiblyCountResultEditText

            if (isVisiblyCountResultEditText){
                countResult.visibility = View.VISIBLE
                previousResult.visibility = View.GONE
            } else {
                previousResult.visibility = View.VISIBLE
                countResult.visibility = View.GONE
            }
        }
    }

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