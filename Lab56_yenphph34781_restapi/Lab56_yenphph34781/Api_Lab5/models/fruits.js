const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Fruits = new Schema({
    name: {
        type: String
    },
    quantity:{
        type: String
    },
    price:{
        type: Number
    },
    status:{
        type: Array
    },
    image:{
        type: Array
    },
    description:{
        type: String
    },
    id_distributor:{
        type: Schema.Types.ObjectId, ref: 'distributor'
    }
}, {
    timestamps: true
})
module.exports = mongoose.model('fruit', Fruits)