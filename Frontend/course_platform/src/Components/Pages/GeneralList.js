import React, {useEffect, useState} from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom';
import "../../Styles/GeneralList.css"

const GeneralList = ({srcLink, text, extendedLink, fkColumn, toShow}) => {
  const [elements, setElements] = useState([])

  useEffect(() => {
    setElements([])
    axios.get(srcLink)
      .then((res)=> {
        // console.log(res.data)
        setElements(res.data)
      })
      .catch((res)=> {
        console.log("Error caught: " + res)
      })
  }, [text])

  const navigate = useNavigate()
  const display = (el) => {
    if (toShow) {
      return toShow.map(key => {
        if (key.includes('>')) {
          let tmp = key.split('>')
          if (el[tmp[0]] !== undefined) {
            return tmp.join(".") + ": " + el[tmp[0]][tmp[1]] +", "
          } else {
            return ""
          }
        }else {
          return key + ": " + el[key]+", "
        }
      })
    } else {
      return Object.entries(el).map((record) => (
        record.join(": ") + ", "
      ))
    }
  }
  return (
    <div>
      <h2>{text}</h2>
      <ul>
      {elements.map((el, idx) => (
        <li className='record' onClick={() => {
          if (extendedLink !== undefined) {
            if (fkColumn.includes('>')) {
              let tmp = fkColumn.split('>')
              navigate(extendedLink + el[tmp[0]][tmp[1]])
            }else {
              navigate(extendedLink + el[fkColumn])
            }
          }
        }}
         key={idx}>
          {display(el)}
        </li>
        ))}
      </ul>
    </div>
  )
}

export default GeneralList;