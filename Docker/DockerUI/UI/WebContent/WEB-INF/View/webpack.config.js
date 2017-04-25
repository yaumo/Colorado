var webpack = require("webpack");

module.exports = {
    entry: './index.js',

    output: {
        path: __dirname +'/public',
        filename: 'index.js',
        publicPath: ''
    },

    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                loader: 'babel-loader',

                query: {
                    presets: ['es2015', 'react']
                }
            },
            {
                test: /\.css$/,
                loader: 'style-loader'
            }, {
                test: /\.css$/,
                loader: 'css-loader',
                query: {
                    modules: true,
                    localIdentName: '[name]__[local]___[hash:base64:5]'
                }
            }

        ]
    },
    node: {
    console: true,
    fs: 'empty',
    net: 'empty',
    tls: 'empty'
  },
    plugins: process.env.NODE_ENV === 'production' ? [
            new webpack.optimize.DedupePlugin(),
            new webpack.optimize.OccurrenceOrderPlugin(),
            new webpack.optimize.UglifyJsPlugin()
        ] : []
}
