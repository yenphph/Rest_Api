const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Users = new Schema({
    username: {
        type: String,
        unique: true,
        maxLength: 225
    },
    password: {
        type: String,
        maxLength: 225
    },
    email:{
        type: String,
        unique: true
    },
    name: {
        type: String
    },
    avatar:{
        type: String
    },
    available: {
        type: Boolean,
        default: false
    }
},{
    timestamps: true,//tạo ra 2 trường createAt va updateAt
})
module.exports = mongoose.model('user', Users);