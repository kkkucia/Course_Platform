import React, {useEffect, useState} from 'react'
import axios from 'axios'
import '../../Styles/Form.css'

const GeneralFunctionPage = (input) => {
  // console.log(input)
  const [responseMessage, setResponseMessage] = useState(Array([]))

  // console.log(responseMessage)
  const handle = (event) => {
    event.preventDefault()
    let data = {}
    for (let i=0; i < input.inputTypes.length; i++) {
      // console.log(event.target[input.requiredData[i]].value)
      // console.log(data)
      data = Object.assign({[input.requiredData[i]]: event.target[input.requiredData[i]].value}, data)
    }
    console.log(data)
    axios.post(input.link, data).then((res) => {
      if (res.status === 200) {
        // console.log(res.data)
        let tmp = res.data
        if (!Array.isArray(res.data)) {
          tmp = [res.data]
        }
        setResponseMessage(tmp)
      }
    })
    .catch((err) => {
      console.log(err.response.data)
      setResponseMessage(["Error with function using data "+JSON.stringify(data) +". Error:" + err.response.data.error])
    })
  }

  useEffect(() => {
    setResponseMessage([])
  }, [input.text])
  
  const display = (el) => {
    return Object.entries(el).map((record) => (
      record.join(": ") + ", "
    ))
  }

  return (
    <div>
      <h2>{input.text}</h2>
      <form onSubmit={handle}>
        {input.requiredData.map((el, key) => (
          <label key={key}>
            {el}
            <input type={input.inputTypes[key]} name={el}></input>
          </label>
        ))}
        <input type='submit'></input>
      </form>
      <div id='response'>
        <ul>
          {responseMessage.map((el, idx) => (
            <li className='record' key={idx}>
              {display(el)}
            </li>
        ))}
        </ul>
      </div>
    </div>
  )
}

export default GeneralFunctionPage;