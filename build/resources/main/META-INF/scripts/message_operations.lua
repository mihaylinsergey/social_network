-- Функция для сохранения сообщения
local function save_message(from, to, text)
     local message_key = string.format("dialog:%s:%s", from, to)
     local message = {
         from = from,
         to = to,
         text = text
     }
     redis.call('RPUSH', message_key, cjson.encode(message))
     return "true"
end

-- Функция для получения списка сообщений
local function get_list_messages(from, to)
    local message_key = string.format("dialog:%s:%s", from, to)
    local messages = redis.call('LRANGE', message_key, 0, -1)
    local result = {}
    for _, message_json in ipairs(messages) do
        local message = cjson.decode(message_json)
        table.insert(result, message)
    end
    return cjson.encode(result)
end

-- Определяем, какую функцию вызывать в зависимости от аргументов
local command = ARGV[1]
if command == "save" then
    return save_message(ARGV[2], ARGV[3], ARGV[4], ARGV[5])
elseif command == "get" then
    return cjson.encode(get_list_messages(ARGV[2], ARGV[3]))
end