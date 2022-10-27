package com.hs1121.deligetsexample.asyncTask

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.ArrayAdapter
import com.hs1121.deligetsexample.databinding.ActivityAsyncTaskBinding

class AsyncTaskActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAsyncTaskBinding
    private val list= arrayListOf("1","2","3","4","5","6","7","8","9","10")
    private val tempList= arrayListOf<String>()
    private lateinit var adapter:ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAsyncTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1, tempList)

        binding.apply {
            progress.max=10;
            progress.progress=0;
            listView.adapter=adapter
        }
        val task=AsyncTaskImp()
        task.execute()


    }
    inner class AsyncTaskImp:AsyncTask<Unit?,Int?,String?,>(){
        override fun doInBackground(vararg p0: Unit?): String? {
            var i=0;
            while(i<list.size) {
                tempList.add(list[i])
//                adapter.add(list[i])
                i++
                publishProgress(i)
                Thread.sleep(1000)
            }
            return "Completed";
        }

        override fun onPreExecute() {
            super.onPreExecute()
            binding.progress.visibility= View.VISIBLE

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            adapter.add(result)
            adapter.notifyDataSetChanged()
            binding.progress.visibility=GONE
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            binding.progress.progress=values[0]?:0
            adapter.notifyDataSetChanged()
        }
    }
}
