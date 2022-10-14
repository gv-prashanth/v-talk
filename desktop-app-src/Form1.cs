using Microsoft.VisualBasic.CompilerServices;
using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Net;
using System.Resources;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace vtalk
{
	[DesignerGenerated]
	public class Form1 : Form
	{
		private IContainer components;

		[AccessedThroughProperty("TextBox1")]
		private TextBox _TextBox1;

		[AccessedThroughProperty("Button1")]
		private Button _Button1;

		[AccessedThroughProperty("RichTextBox1")]
		private RichTextBox _RichTextBox1;

		[AccessedThroughProperty("Timer1")]
		private Timer _Timer1;

		[AccessedThroughProperty("TextBox2")]
		private TextBox _TextBox2;

		[AccessedThroughProperty("Label2")]
		private Label _Label2;

		[AccessedThroughProperty("Label1")]
		private Label _Label1;

		[AccessedThroughProperty("BackgroundWorker1")]
		private BackgroundWorker _BackgroundWorker1;

		[AccessedThroughProperty("BackgroundWorker2")]
		private BackgroundWorker _BackgroundWorker2;

		private int lastchatlin;

		private string lastchat;

		private string inptchat;

		private int recchatlin;

		private string recchat;

        private static string serviceDomain = "https://v-talk.vadrin.com/";
		internal virtual BackgroundWorker BackgroundWorker1
		{
			[DebuggerNonUserCode]
			get
			{
				return this._BackgroundWorker1;
			}
			[DebuggerNonUserCode]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				Form1 form1 = this;
				DoWorkEventHandler doWorkEventHandler = new DoWorkEventHandler(form1.BackgroundWorker1_DoWork);
				if (this._BackgroundWorker1 != null)
				{
					this._BackgroundWorker1.DoWork -= doWorkEventHandler;
				}
				this._BackgroundWorker1 = value;
				if (this._BackgroundWorker1 != null)
				{
					this._BackgroundWorker1.DoWork += doWorkEventHandler;
				}
			}
		}

		internal virtual BackgroundWorker BackgroundWorker2
		{
			[DebuggerNonUserCode]
			get
			{
				return this._BackgroundWorker2;
			}
			[DebuggerNonUserCode]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				Form1 form1 = this;
				DoWorkEventHandler doWorkEventHandler = new DoWorkEventHandler(form1.BackgroundWorker2_DoWork);
				if (this._BackgroundWorker2 != null)
				{
					this._BackgroundWorker2.DoWork -= doWorkEventHandler;
				}
				this._BackgroundWorker2 = value;
				if (this._BackgroundWorker2 != null)
				{
					this._BackgroundWorker2.DoWork += doWorkEventHandler;
				}
			}
		}

		internal virtual Button Button1
		{
			[DebuggerNonUserCode]
			get
			{
				return this._Button1;
			}
			[DebuggerNonUserCode]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				Form1 form1 = this;
				EventHandler eventHandler = new EventHandler(form1.Button1_Click);
				if (this._Button1 != null)
				{
					this._Button1.Click -= eventHandler;
				}
				this._Button1 = value;
				if (this._Button1 != null)
				{
					this._Button1.Click += eventHandler;
				}
			}
		}

		internal virtual Label Label1
		{
			[DebuggerNonUserCode]
			get
			{
				return this._Label1;
			}
			[DebuggerNonUserCode]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label1 = value;
			}
		}

		internal virtual Label Label2
		{
			[DebuggerNonUserCode]
			get
			{
				return this._Label2;
			}
			[DebuggerNonUserCode]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._Label2 = value;
			}
		}

		internal virtual RichTextBox RichTextBox1
		{
			[DebuggerNonUserCode]
			get
			{
				return this._RichTextBox1;
			}
			[DebuggerNonUserCode]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._RichTextBox1 = value;
			}
		}

		internal virtual TextBox TextBox1
		{
			[DebuggerNonUserCode]
			get
			{
				return this._TextBox1;
			}
			[DebuggerNonUserCode]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				Form1 form1 = this;
				KeyPressEventHandler keyPressEventHandler = new KeyPressEventHandler(form1.TextBox1_KeyPress);
				if (this._TextBox1 != null)
				{
					this._TextBox1.KeyPress -= keyPressEventHandler;
				}
				this._TextBox1 = value;
				if (this._TextBox1 != null)
				{
					this._TextBox1.KeyPress += keyPressEventHandler;
				}
			}
		}

		internal virtual TextBox TextBox2
		{
			[DebuggerNonUserCode]
			get
			{
				return this._TextBox2;
			}
			[DebuggerNonUserCode]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._TextBox2 = value;
			}
		}

		internal virtual Timer Timer1
		{
			[DebuggerNonUserCode]
			get
			{
				return this._Timer1;
			}
			[DebuggerNonUserCode]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				Form1 form1 = this;
				EventHandler eventHandler = new EventHandler(form1.Timer1_Tick);
				if (this._Timer1 != null)
				{
					this._Timer1.Tick -= eventHandler;
				}
				this._Timer1 = value;
				if (this._Timer1 != null)
				{
					this._Timer1.Tick += eventHandler;
				}
			}
		}

		[DebuggerNonUserCode]
		public Form1()
		{
			Form1 form1 = this;
			base.Load += new EventHandler(form1.Form1_Load);
			this.InitializeComponent();
		}

		private void BackgroundWorker1_DoWork(object sender, DoWorkEventArgs e)
		{
			this.sendchat(this.inptchat);
		}

		private void BackgroundWorker2_DoWork(object sender, DoWorkEventArgs e)
		{
			this.recchatlin = this.getchatlin();
			if (this.lastchatlin != this.recchatlin)
			{
				this.recchat = this.getchat();
				this.lastchatlin = this.recchatlin;
			}
		}

		private void Button1_Click(object sender, EventArgs e)
		{
			if (Operators.CompareString(this.TextBox1.Text, "", false) != 0)
			{
				this.inptchat = string.Concat(this.TextBox2.Text, " : ", this.TextBox1.Text);
				if (!this.BackgroundWorker1.IsBusy)
				{
					this.BackgroundWorker1.RunWorkerAsync();
				}
				this.TextBox1.Text = "";
				this.TextBox1.Focus();
			}
		}

		[DebuggerNonUserCode]
		protected override void Dispose(bool disposing)
		{
			try
			{
				if (disposing && this.components != null)
				{
					this.components.Dispose();
				}
			}
			finally
			{
				base.Dispose(disposing);
			}
		}

		private void Form1_Load(object sender, EventArgs e)
		{
			this.Timer1.Enabled = true;
		}

		private string getchat()
		{
			string str;
			try
			{
				WebResponse response = WebRequest.Create(serviceDomain + "receive").GetResponse();
				StreamReader streamReader = new StreamReader(response.GetResponseStream());
				string end = streamReader.ReadToEnd();
				streamReader.Close();
				response.Close();
				str = end;
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				str = "Error Connecting to server";
				ProjectData.ClearProjectError();
			}
			return str;
		}

		private int getchatlin()
		{
			int integer;
			try
			{
                WebResponse response = WebRequest.Create(serviceDomain + "chatline").GetResponse();
				StreamReader streamReader = new StreamReader(response.GetResponseStream());
				string end = streamReader.ReadToEnd();
				streamReader.Close();
				response.Close();
				integer = Conversions.ToInteger(end);
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				integer = 0;
				ProjectData.ClearProjectError();
			}
			return integer;
		}

		[DebuggerStepThrough]
		private void InitializeComponent()
		{
			this.components = new System.ComponentModel.Container();
			ComponentResourceManager componentResourceManager = new ComponentResourceManager(typeof(Form1));
			this.TextBox1 = new TextBox();
			this.Button1 = new Button();
			this.RichTextBox1 = new RichTextBox();
			this.Timer1 = new Timer(this.components);
			this.TextBox2 = new TextBox();
			this.Label2 = new Label();
			this.Label1 = new Label();
			this.BackgroundWorker1 = new BackgroundWorker();
			this.BackgroundWorker2 = new BackgroundWorker();
			this.SuspendLayout();
			this.TextBox1.BackColor = Color.FromArgb(216, 238, 160);
			this.TextBox1.BorderStyle = BorderStyle.None;
			this.TextBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.TextBox1.ForeColor = Color.MediumBlue;
			TextBox textBox1 = this.TextBox1;
			Point point = new Point(22, 375);
			textBox1.Location = point;
			this.TextBox1.Name = "TextBox1";
			TextBox textBox = this.TextBox1;
			System.Drawing.Size size = new System.Drawing.Size(240, 16);
			textBox.Size = size;
			this.TextBox1.TabIndex = 0;
			this.Button1.BackColor = Color.OrangeRed;
			this.Button1.BackgroundImage = (Image)componentResourceManager.GetObject("Button1.BackgroundImage");
			this.Button1.Font = new System.Drawing.Font("Comic Sans MS", 15.75f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.Button1.ForeColor = SystemColors.ButtonFace;
			Button button1 = this.Button1;
			point = new Point(99, 405);
			button1.Location = point;
			this.Button1.Name = "Button1";
			Button button = this.Button1;
			size = new System.Drawing.Size(87, 36);
			button.Size = size;
			this.Button1.TabIndex = 1;
			this.Button1.Text = "Send";
			this.Button1.UseVisualStyleBackColor = false;
			this.RichTextBox1.BackColor = Color.FromArgb(216, 238, 160);
			this.RichTextBox1.BorderStyle = BorderStyle.None;
			this.RichTextBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.RichTextBox1.ForeColor = Color.MediumBlue;
			RichTextBox richTextBox1 = this.RichTextBox1;
			point = new Point(22, 195);
			richTextBox1.Location = point;
			this.RichTextBox1.Name = "RichTextBox1";
			this.RichTextBox1.ReadOnly = true;
			RichTextBox richTextBox = this.RichTextBox1;
			size = new System.Drawing.Size(240, 157);
			richTextBox.Size = size;
			this.RichTextBox1.TabIndex = 3;
			this.RichTextBox1.Text = "";
			this.Timer1.Interval = 300;
			this.TextBox2.BackColor = SystemColors.Control;
			this.TextBox2.BorderStyle = BorderStyle.None;
			this.TextBox2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10f, FontStyle.Bold, GraphicsUnit.Point, 0);
			this.TextBox2.ForeColor = Color.Maroon;
			TextBox textBox2 = this.TextBox2;
			point = new Point(2, 3);
			textBox2.Location = point;
			this.TextBox2.Name = "TextBox2";
			TextBox textBox21 = this.TextBox2;
			size = new System.Drawing.Size(57, 16);
			textBox21.Size = size;
			this.TextBox2.TabIndex = 4;
			this.TextBox2.Text = "User1";
			this.Label2.AutoSize = true;
			this.Label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 6.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			Label label2 = this.Label2;
			point = new Point(31, 444);
			label2.Location = point;
			this.Label2.Name = "Label2";
			Label label = this.Label2;
			size = new System.Drawing.Size(231, 12);
			label.Size = size;
			this.Label2.TabIndex = 6;
			this.Label2.Text = "Copyright © 2009 G.V.Prashanth. All Rights Reserved.";
			this.Label1.AutoSize = true;
			Label label1 = this.Label1;
			point = new Point(232, 141);
			label1.Location = point;
			this.Label1.Name = "Label1";
			Label label11 = this.Label1;
			size = new System.Drawing.Size(41, 13);
			label11.Size = size;
			this.Label1.TabIndex = 7;
			this.Label1.Text = "Ver 1.9";
			this.AutoScaleDimensions = new SizeF(6f, 13f);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.BackgroundImage = (Image)componentResourceManager.GetObject("$this.BackgroundImage");
			this.BackgroundImageLayout = ImageLayout.None;
			size = new System.Drawing.Size(285, 458);
			this.ClientSize = size;
			this.Controls.Add(this.Label1);
			this.Controls.Add(this.Label2);
			this.Controls.Add(this.TextBox2);
			this.Controls.Add(this.Button1);
			this.Controls.Add(this.TextBox1);
			this.Controls.Add(this.RichTextBox1);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
			this.Icon = (System.Drawing.Icon)componentResourceManager.GetObject("$this.Icon");
			this.MaximizeBox = false;
			this.Name = "Form1";
			this.Text = "V-Talk";
			this.ResumeLayout(false);
			this.PerformLayout();
		}

		private void sendchat(string inpt)
		{
			try
			{
                WebRequest webRequest = WebRequest.Create(string.Concat(serviceDomain + "send?inptchat=", inpt));
				webRequest.GetResponse().Close();
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				MessageBox.Show("Error Connecting to server", "Error");
				ProjectData.ClearProjectError();
			}
		}

		private void TextBox1_KeyPress(object sender, KeyPressEventArgs e)
		{
			if (e.KeyChar == '\r')
			{
				this.Button1_Click(this, EventArgs.Empty);
			}
		}

		private void Timer1_Tick(object sender, EventArgs e)
		{
			if (!this.BackgroundWorker2.IsBusy)
			{
				this.BackgroundWorker2.RunWorkerAsync();
			}
			if (Operators.CompareString(this.lastchat, this.recchat, false) != 0)
			{
				this.RichTextBox1.Text = string.Concat(this.recchat, "\r\n", this.RichTextBox1.Text);
				this.lastchat = this.recchat;
			}
		}
	}
}