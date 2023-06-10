import React, {useEffect, useState} from 'react'
import axios from 'axios'

const GeneralList = ({srcLink, text}) => {
  const [elements, setElements] = useState([])

  useEffect(() => {
    axios.get(srcLink)
      .then((res)=> {
        // console.log(res.data)
        setElements(res.data)
      })
      .catch((res)=> {
        console.log("Error caught: " + res)
      })
  }, [srcLink])
  
  return (
    <div>
      <h2>{text}</h2>
      <ul>
      {elements.map((el, idx) => (
        <li key={idx}>{JSON.stringify(el)}</li>
        ))}
      </ul>
    </div>
  )
}

export default GeneralList;