# creat new variable
filename = ARGV.first
# Open text file
txt = open(filename)
# print text
puts "Here's your file #{filename}:"
print txt.read
# print text
print "Type the filename again: "
# creat new variable with file name 
file_again = $stdin.gets.chomp
# Open text file
txt_again = open(file_again)

print txt_again.read