import React, { useEffect, useState } from 'react'
import axios from 'axios'
import '../../Styles/Form.css'
import '../../Styles/GeneralFunction.css'

const GeneralFunctionPage = (input) => {
  const [responseMessage, setResponseMessage] = useState(Array([]))

  useEffect(() => {
    setResponseMessage([])
  }, [input.text])

  const handle = (event) => {
    event.preventDefault()
    let data = {}
    for (let i = 0; i < input.inputTypes.length; i++) {
      data = Object.assign({ [input.requiredData[i]]: event.target[input.requiredData[i]].value }, data)
    }
    axios.get(input.srcLink, { params: data }).then((res) => {
      if (res.status === 200) {
        let tmp = res.data
        if (!Array.isArray(tmp)) {
          tmp = [res.data]
        }
        setResponseMessage(tmp)
      }
    })
      .catch((err) => {
        console.log(err.response.data)
        setResponseMessage(["Error with function using data " + JSON.stringify(data) + ". Error:" + err.response.data.error])
      })
  }

  const displayData = (data, idx) => {
    if (typeof (data) == 'object') {
      return Object.entries(data).map((record, key) => {
        return <>
          <b key={idx + "-" + key}>
            {record[0]}:
          </b>
          {record[1]}
          {key % 4 === 3 ? <br /> : ""} 
        </>
      })
    } else {
      return <b>{data}</b>
    }
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
      <div id='response'>
        <ul>
          {responseMessage.map((el, idx) => (
            <li className='record' key={idx}>
              {displayData(el, idx)}
            </li>
          ))}
        </ul>
      </div>
    </div>
  )
}

export default GeneralFunctionPage;