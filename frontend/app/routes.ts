import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [index("routes/home.tsx"),
    route('tasks', 'routes/tasks.tsx'),
//   route('tasks/new', 'routes/tasks.new.tsx'),
//   route('tasks/:id/edit', 'routes/tasks.$id.edit.tsx')
] satisfies RouteConfig;
