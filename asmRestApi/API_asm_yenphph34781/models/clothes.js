const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Clothes = new Schema({
    name: {
        type: String
    },
    description:{
        type: String
    },
    price:{
        type: Number
    },
    image:{
        type: Array
    }
}, {
    timestamps: true
})
module.exports = mongoose.model('clothe', Clothes );
