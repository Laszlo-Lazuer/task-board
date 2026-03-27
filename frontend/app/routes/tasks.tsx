import { useLoaderData, Link, type LoaderFunctionArgs } from "react-router";

type Task = {
    id: number;
    title: string;
    description: string | null;
    status: string;
    createdAt: string;
    updatedAt: string;
};

export async function loader(_args: LoaderFunctionArgs) {
    const response = await fetch('http://localhost:8080/api/tasks');
    if (!response.ok) {
        throw new Response('failed to fetch tasks', { status: response.status });
    }
    const tasks: Task[] = await response.json();
    return tasks;
}


export default function TasksPage() {
  const tasks = useLoaderData<typeof loader>();

      return (
    <div style={{ padding: '1rem' }}>
      <h1>Tasks</h1>
      <Link to="/tasks/new">
        <button>+ New Task</button>
      </Link>
      {tasks.length === 0 ? (
        <p>No tasks yet. Create one!</p>
      ) : (
        <ul style={{ listStyle: 'none', padding: 0 }}>
          {tasks.map((task) => (
            <li
              key={task.id}
              style={{
                border: '1px solid #ccc',
                borderRadius: '4px',
                padding: '0.75rem',
                marginBottom: '0.5rem',
              }}
            >
              <strong>{task.title}</strong>
              <span style={{ marginLeft: '1rem', color: '#666' }}>
                {task.status}
              </span>
              {task.description && <p style={{ margin: '0.25rem 0 0' }}>{task.description}</p>}
              <div style={{ marginTop: '0.5rem' }}>
                <Link to={`/tasks/${task.id}/edit`}>Edit</Link>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
