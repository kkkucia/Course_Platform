import React, { useEffect, useState } from 'react'
import axios from 'axios'
import '../../Styles/Form.css'

const GeneralProcedurePage = (input) => {
  const [responseMessage, setResponseMessage] = useState("")

  useEffect(() => {
    setResponseMessage("")
  }, [input.text])

  const handle = (event) => {
    event.preventDefault()
    let data = {}
    for (let i = 0; i < input.inputTypes.length; i++) {
      data = Object.assign({ [input.requiredData[i]]: event.target[input.requiredData[i]].value }, data)
    }
    console.log(data)
    axios.post(input.srcLink, data, { headers: { "Content-Type": "application/json" } }).then((res) => {
      if (res.status === 200) {
        setResponseMessage("Procedure run correctly with data " + JSON.stringify(data))
      }
    })
      .catch((err) => {
        console.log(err.response)
        setResponseMessage("Error with procedure using data " + JSON.stringify(data) + ". Error:" + err.response.data.error)
      })
  }

  return (
    <div>
      <h2>{input.text}</h2>
      <form onSubmit={handle}>
        {input.requiredData.map((el, key) => (
          <label key={key}>
            {el}:
            <input type={input.inputTypes[key]} name={el}></input>
          </label>
        ))}
        <input type='submit'></input>
      </form>
      <div id='response'>{responseMessage}</div>
    </div>
  )
}

export default GeneralProcedurePage;