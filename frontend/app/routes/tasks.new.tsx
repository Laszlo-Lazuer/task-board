import { Form, redirect, useActionData, useNavigation } from 'react-router';
import type { ActionFunctionArgs } from 'react-router';

type ActionError = { error: string };

export async function action({ request }: ActionFunctionArgs) {
  const formData = await request.formData();
  const title = formData.get('title');
  const description = formData.get('description');

  if (!title || typeof title !== 'string' || title.trim() === '') {
    return { error: 'Title is required' } satisfies ActionError;
  }

  const task = {
    title: title.trim(),
    description: typeof description === 'string' ? description.trim() : null,
    status: 'TODO',
  };

  const response = await fetch('http://localhost:8080/api/tasks', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(task),
  });

  if (!response.ok) {
    throw new Response('Failed to create task', { status: response.status });
  }

  return redirect('/tasks');
}

export default function NewTaskPage() {
  const actionData = useActionData<ActionError | undefined>();
  const navigation = useNavigation();
  const isSubmitting = navigation.state === 'submitting';

  return (
    <div style={{ padding: '1rem', maxWidth: '480px' }}>
      <h1>Create New Task</h1>
      <Form method="post">
        <div style={{ marginBottom: '0.75rem' }}>
          <label htmlFor="title">Title *</label>
          <br />
          <input
            id="title"
            name="title"
            type="text"
            required
            style={{ width: '100%', padding: '0.4rem' }}
          />
        </div>
        <div style={{ marginBottom: '0.75rem' }}>
          <label htmlFor="description">Description</label>
          <br />
          <textarea
            id="description"
            name="description"
            rows={3}
            style={{ width: '100%', padding: '0.4rem' }}
          />
        </div>
        {actionData?.error && (
          <p style={{ color: 'red' }}>{actionData.error}</p>
        )}
        <button type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Creating...' : 'Create Task'}
        </button>
      </Form>
    </div>
  );
}