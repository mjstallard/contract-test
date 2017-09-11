require 'sinatra/base'
require "sinatra/json"
require 'concurrent'
require 'json'

class App < Sinatra::Application
  configure do
    set(:foos, Concurrent::Array.new)
  end

  get "/v1/foos" do
    json(App.foos)
  end

  post "/v1/foos" do
    foo = JSON.parse(request.body.read)
    App.foos << foo
    json(foo)
  end


  run! if app_file == $0
end
