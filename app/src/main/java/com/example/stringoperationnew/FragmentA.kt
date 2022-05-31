package com.example.stringoperationnew

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import java.lang.Exception

class FragmentA : Fragment() {

    private lateinit var reverseButton: Button
    private lateinit var splitButton: Button
    private lateinit var appendButton: Button
    private lateinit var splitComma: Button
    private lateinit var resultText: TextView
    private lateinit var result: TextView
    private lateinit var optionButton: Button
    private var operation: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)
        reverseButton = view.findViewById(R.id.reverseButton)
        splitButton = view.findViewById(R.id.splitButton)
        appendButton = view.findViewById(R.id.appendButton)
        splitComma = view.findViewById(R.id.splitcomButton)
        resultText = view.findViewById(R.id.resultText)
        result = view.findViewById(R.id.result)
        optionButton = view.findViewById(R.id.optionButton)
        if(savedInstanceState != null) {
            result.text = savedInstanceState.getString("result")
            MainActivity.isResultPage = savedInstanceState.getBoolean("isResultPage")
            if(MainActivity.isResultPage){
                displayResult()
            }
            else {
                displayHome()
            }
        }
        appendButton.setOnClickListener {
            operation = "append"
            passOperation("append")
        }
        reverseButton.setOnClickListener {
            operation = "reverse"
            passOperation("reverse")
        }
        splitButton.setOnClickListener {
            operation = "split"
            passOperation("split")
        }
        splitComma.setOnClickListener {
            operation = "splitComma"
            passOperation("splitComma")
        }
        optionButton.setOnClickListener {
            MainActivity.isResultPage = false
            displayHome()
            result.text = ""
        }
        setFragmentResultListener("0"){ requestKey, bundle ->
            result.text = bundle.getString("output")
            displayResult()
        }
        return view
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isResultPage", MainActivity.isResultPage)
        try {
            outState.putString("result", result.text.toString())
        }
        catch(e: Exception) {
            outState.putString("result", "")
        }
    }
    private fun passOperation(operation : String) {
        MainActivity.isFragB = false
        val orientation = resources.configuration.orientation
        val bundle = Bundle()
        bundle.putString("operation", operation)
        val transaction = parentFragmentManager.beginTransaction()
        val fragmentB = FragmentB()
        fragmentB.arguments = bundle
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.fragmentB, fragmentB)
        } else if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.fragment_container, fragmentB )
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }
    fun displayHome() {
        appendButton.visibility = View.VISIBLE
        reverseButton.visibility = View.VISIBLE
        splitButton.visibility = View.VISIBLE
        splitComma.visibility = View.VISIBLE
        resultText.visibility = View.GONE
        result.visibility = View.GONE
        optionButton.visibility = View.GONE
    }
    fun displayResult() {
        appendButton.visibility = View.GONE
        reverseButton.visibility = View.GONE
        splitButton.visibility = View.GONE
        splitComma.visibility = View.GONE
        resultText.visibility = View.VISIBLE
        result.visibility = View.VISIBLE
        optionButton.visibility = View.VISIBLE
    }
}