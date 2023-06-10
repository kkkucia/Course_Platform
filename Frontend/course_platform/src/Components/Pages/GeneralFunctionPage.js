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
    axios.get(input.link, {params:data}).then((res) => {
      if (res.status === 200) {
        // setResponseMessage("Procedure run correctly with data "+JSON.stringify(data))
        console.log(res.data)
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
          {responseMessage.map((el, key) => (
            <li key={key}>
              {JSON.stringify(el)}
            </li>
          ))}
        </ul>
      </div>
    </div>
  )
}

export default GeneralFunctionPage;