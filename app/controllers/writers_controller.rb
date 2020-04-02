class WritersController < ApplicationController
  before_action :set_writer, only: [:show, :edit, :update, :destroy]

  # GET /writers
  # GET /writers.json
  def index
    @writers = Writer.all
  end

  # GET /writers/1
  # GET /writers/1.json
  def show
  end

  # GET /writers/new
  def new
    @writer = Writer.new
  end

  # GET /writers/1/edit
  def edit
  end

  # POST /writers
  # POST /writers.json
  def create
    @writer = Writer.new(writer_params)
    @writer.website_shortened = create_website_shortened

    respond_to do |format|
      if @writer.save
        format.html { redirect_to @writer, notice: 'Writer was successfully created.' }
        format.json { render :show, status: :created, location: @writer }
      else
        format.html { render :new }
        format.json { render json: @writer.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /writers/1
  # PATCH/PUT /writers/1.json
  def update
    changed_website = (@writer.website != writer_params[:website])

    respond_to do |format|
      if (changed_website) 
        @writer.website_shortened = create_website_shortened
        params[:website_shortened] = @writer.website_shortened

        puts '@writer.website_shortened', @writer.website_shortened
        puts 'writer_params', writer_params
      end

      if @writer.update(writer_params)
        format.html { redirect_to @writer, notice: 'Writer was successfully updated.' }
        format.json { render :show, status: :ok, location: @writer }
      else
        format.html { render :edit }
        format.json { render json: @writer.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /writers/1
  # DELETE /writers/1.json
  def destroy
    @writer.destroy
    respond_to do |format|
      format.html { redirect_to writers_url, notice: 'Writer was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_writer
      @writer = Writer.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def writer_params
      params.require(:writer).permit(:name, :website, :website_shortened, :website_headings)
    end

    def create_website_shortened
      client = Bitly::API::Client.new(token: "7000ae3c10755b540f1e1b05329808431ddfa975")
      bitlink = client.shorten(long_url: writer_params[:website])
      return bitlink.link
    end
end
