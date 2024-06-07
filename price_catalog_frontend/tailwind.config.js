import withMT from "@material-tailwind/html/utils/withMT";

/** @type {import('tailwindcss').Config} */
module.exports = withMT({
  content: ["./src/**/*.{html,ts}", "./node_modules/flowbite/**/*.js"],
  //important: true,
  theme: {
    extend: {},
  },
  plugins: [require("flowbite/plugin")],
  corePlugins: { preflight: false },
});
