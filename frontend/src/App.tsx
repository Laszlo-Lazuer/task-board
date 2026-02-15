import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
interface Greeting {
  id: number;
  content: string;
}

function App() {
  const [count, setCount] = useState(0)
  const [data, setData] = useState<Greeting | null>(null);

  useEffect(()=> {
  const load = async () => {
    const res = await fetch('http://localhost:8080/greeting')
    const data = await res.json()
    console.log(data)
    setData(data);
  }
  load()

  }, [])

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div>
        <p>ID: {data?.id}</p>
        <p>Content: {data?.content}</p>
      </div>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        {data && <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>}
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
